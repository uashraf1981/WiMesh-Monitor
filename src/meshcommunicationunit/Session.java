/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meshcommunicationunit;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import global.CONSTANTS;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import meshmonitoringunit.RouterInfo;

/**
 *
 * @author ICTWimesh
 */
public class Session {

    public static com.jcraft.jsch.Session[] session = new com.jcraft.jsch.Session[CONSTANTS.ROUTERS];
// Generate jsch Connection with each connected routers.
    public Boolean generateShellSessions() {
        Channel.channelShell = new com.jcraft.jsch.Channel[CONSTANTS.ROUTERS];
        for (int index = 0; index < CONSTANTS.ROUTERS; index++) {

            String ID = RouterInfo.routerList.get(index).ID;
            String IP = RouterInfo.routerList.get(index).IP;
            try {
                JSch jsch = new JSch();
                System.out.println("Try to connect to " + IP);
                session[index] = jsch.getSession(CONSTANTS.USER_ID, IP, 22);
                session[index].setPassword(CONSTANTS.PASSWORD);
                Properties prop = new Properties();
                prop.setProperty("StrictHostKeyChecking", "no");
                session[index].setConfig(prop);
                session[index].connect();
                // number of channels is equal to the number of different tabs int the system //
                // Globals.CHANNELS.channelExec = new Channel[CONSTANTS.NO_OF_TABS];            
                // for (int channelNumb = 0; channelNumb < 1; channelNumb++) {
                // Open Shell Connection with Router's.
                Channel.channelShell[index] = session[index].openChannel("shell");
                Channel.channelShell[index].setInputStream(System.in);//InOutStream.in[channelNumb]
                InputStream in = new PipedInputStream();
                global.CONSTANTS.pin[index] = new PipedOutputStream((PipedInputStream) in);

                Channel.channelShell[index].setInputStream(in);
                Channel.channelShell[index].connect();
                Channel.channelShell[index].getSession().sendKeepAliveMsg();
                RouterInfo.routerList.get(index).isAlive = true;
            } catch (JSchException e) {
                RouterInfo.routerList.get(index).isAlive = false;
                System.out.println(e);
            } catch (Exception ex) {
                Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public String generateExecCommands(String host, String Command) {
        try {
            com.jcraft.jsch.Session session;
            JSch jsch = new JSch();
            session = jsch.getSession(CONSTANTS.USER_ID, host, 22);
            session.setPassword(CONSTANTS.PASSWORD);
            Properties prop = new Properties();
            prop.setProperty("StrictHostKeyChecking", "no");
            session.setConfig(prop);
            session.connect();
            String command = Command;
            com.jcraft.jsch.Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            String str = "";
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    str += (new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
            return str;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public String upload(String host, String[] file, String Path) {
        try {
            com.jcraft.jsch.Session session;
            JSch jsch = new JSch();
            session = jsch.getSession(CONSTANTS.USER_ID, host, 22);
            session.setPassword(CONSTANTS.PASSWORD);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            com.jcraft.jsch.Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd("/");
            for (int i = 0; i < file.length; i++) {
                File f1 = new File(file[i]);
                channelSftp.put(new FileInputStream(f1), f1.getName(), ChannelSftp.OVERWRITE);
                Thread.sleep(1000);
            }
            channelSftp.exit();
            session.disconnect();
            return "Files Uploaded Successfully";

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public void reGenerateSession(int index, String IP) throws JSchException, IOException, Exception {

    }
}
