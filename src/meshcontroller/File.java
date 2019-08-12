/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meshcontroller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import global.CONSTANTS;
import global.SshCommand;
import gui.WiMeshMainForm;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ICTWimesh
 */
public class File {

    public String chooseFilesLocation(boolean bool) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
      //  chooser.setDialogTitle("backup.xml");
        if(!bool){
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        
        }else{
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
        }
        chooser.setAcceptAllFileFilterUsed(false);
        //FileFilter filter = new FileNameExtensionFilter("XML File", "xml");
        //chooser.addChoosableFileFilter(filter);
        //    
        if (chooser.showOpenDialog(WiMeshMainForm.Network_Tab) == JFileChooser.APPROVE_OPTION) {
            //     +  chooser.getCurrentDirectory());
            return chooser.getSelectedFile().toString();
        } else {
            //System.out.println("No Selection ");
            return null;
        }
    }
                                    //D:\wireless                       //D:\wireless
    public String uploadFile(String fileTobeplace, String IP, String DestinationFolder) {
//      System.err.println("usage: java ScpTo file1 user@remotehost:file2");
//      System.exit(-1);
//    }      
        
        FileInputStream fis = null;
        try {
            String lfile =fileTobeplace;

            String rfile = DestinationFolder; // etc/config/

            JSch jsch = new JSch();
            Session session ;//= jsch.getSession(user, host, 22);
            session = jsch.getSession(CONSTANTS.USER_ID, IP, 22);
            session.setPassword(CONSTANTS.PASSWORD);

            session.setPassword(CONSTANTS.PASSWORD);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            boolean ptimestamp = true;
            // exec 'scp -t rfile' remotely
            //String command = "cd ../;scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
            String command = SshCommand.GOBACK+";scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
                
            if (checkAck(in) != 0) {
                // System.exit(0);
            }

            java.io.File _lfile = new java.io.File(lfile);
            if (ptimestamp) {
                command = "T " + (_lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    //   System.exit(0);
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = _lfile.length();
            command = "C0644 " + filesize + " ";
            if (lfile.lastIndexOf('\\') > 0) {
                command += lfile.substring(lfile.lastIndexOf('\\') + 1); //E:\New folder\18-3-14\WimishMonitor\build.xml
            } else {
                command += lfile;
            }
            
            command += "\n";
            
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                //    System.exit(0);
            }

            // send a content of lfile
            fis = new FileInputStream(lfile);
            byte[] buf = new byte[1024];
            while (true)
            {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0)
                {
                    break;
                }
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                // System.exit(0);
            }
            out.close();

            channel.disconnect();
            session.disconnect();
            //System.exit(0);
            return "Uploaded";
        } catch (JSchException | IOException e) {
            System.out.println(e);
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ee) {
            }
            return e.getMessage();
        }
    }

    static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1 end of the stream
        if (b == 0) {
            return b;
        }
        if (b == -1) {
            return b;
        }

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
                status = sb.toString();
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
                status = sb.toString();
            }
        }
        return b;
    }
    static String status = "";
    public String download(String fileToDownload, String IP, String DestinationFolder) throws InterruptedException {
        FileOutputStream fos = null;
        try {
          //  String user = CONSTANTS.USER_ID;
          //  String host = IP;
            String rfile = fileToDownload;
            String lfile = DestinationFolder;

            String prefix = null;
            if (new java.io.File(lfile).isDirectory()) {
                prefix = lfile + java.io.File.separator;
            }
            JSch jsch = new JSch();
            Session session ;//= jsch.getSession(user, host, 22);
                
            session = jsch.getSession(CONSTANTS.USER_ID, IP, 22);
            session.setPassword(CONSTANTS.PASSWORD);
            session.setPassword(CONSTANTS.PASSWORD);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
//      username and password will be given via UserInfo interface.
//      UserInfo ui=new MyUserInfo();
//      session.setUserInfo(ui);
            session.connect();

            // exec 'scp -f rfile' remotely
            String command = "cd ..;scp -f " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            //global.CONSTANTS.pin[0].write((command+"\n").getBytes());
            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] buf = new byte[1024];
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }
                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ') {
                        break;
                    }
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file = null;
                for (int i = 0;; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }

	//System.out.println("filesize="+filesize+", file="+file);
                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();

                // read a content of lfile
                fos = new FileOutputStream(prefix == null ? lfile : prefix + file);
                int foo;
                while (true) {
                    if (buf.length < filesize) {
                        foo = buf.length;
                    } else {
                        foo = (int) filesize;
                    }
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error 
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L) {
                        break;
                    }
                }
                fos.close();
                fos = null;

                if (checkAck(in) != 0) {
                    //System.exit(0);
                }
                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }
            session.disconnect();
          return status;

            // System.exit(0);
        } catch (JSchException | IOException e) {
            System.out.println(e);
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ee) {
            }
              return "the following error while Downloading file \n" + e.getMessage();
        }
    }
    
    public String compress(String IP,String RemoteDirectoryForBackup){ 
    FileOutputStream fos=null;
    try{
     // String user=CONSTANTS.USER_ID;//arg[0].substring(0, arg[0].indexOf('@'));
      Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String TodayDate = sdf.format(date);
    
      String rfile=TodayDate+".zip";//"website-backup-2010-11-31.zip";//arg[0].substring(arg[0].indexOf(':')+1);
      String Direc= RemoteDirectoryForBackup;

      JSch jsch=new JSch();
      Session session;//=jsch.getSession(user, IP, 22);
          
                    session = jsch.getSession(CONSTANTS.USER_ID, IP, 22);
                    session.setPassword(CONSTANTS.PASSWORD);
       // session.setPassword(CONSTANTS.PASSWORD);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
      session.connect();
      
      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand("cd ..;zip -r "+rfile+" "+ Direc +"/*;");
      channel.connect(); 
      Thread.sleep(1000);
      session.disconnect();
     // System.exit(0);
      return rfile;
    }
    catch(JSchException | InterruptedException e){
      System.out.println(e);
      try{if(fos!=null)fos.close();}catch(IOException ee){}
      return null;
    }
  }
    
}
