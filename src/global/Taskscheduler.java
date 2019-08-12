/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.awt.Label;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import meshmonitoringunit.RouterInfo;

/**
 *
 * @author ICTWimesh
 */
public class Taskscheduler extends Thread{

    Timer timerbackScript = new Timer("wimeshTimer");
    Timer timerbackScript2 = new Timer("wimeshTimer");

    public void run(JTabbedPane Logging_LogsTabPane) {
        backGroundScripts.setInterfaceForRoutersLogs(Logging_LogsTabPane);
        backGroundScripts t = new backGroundScripts();
        timerbackScript.schedule(t, 15000, CONSTANTS.TIMEINTERVAL_LOGS);
        BackGroundScriptsToFile t2 = new BackGroundScriptsToFile();
        timerbackScript2.schedule(t2, 5000, CONSTANTS.TIMEINTERVAL_FILES);
    }
}

class backGroundScripts extends TimerTask {

    public static JTabbedPane tabb;
    public static ArrayList<JTextArea> logsLists = new ArrayList();

    public static void setInterfaceForRoutersLogs(JTabbedPane Logging_LogsTabPane) {
      
        tabb = Logging_LogsTabPane;
        for(int i = 0; i < CONSTANTS.ROUTERS; i++) {
            JTextArea jta = new JTextArea();
            // jta.setText("<b>Date\tTime\tRouter ID\tRouter IP\tRadio IP\tmask\tRX Packtes\tTX packets</b>");
            JScrollPane sp = new JScrollPane(jta);
            tabb.add(RouterInfo.routerList.get(i).IP, sp);
            logsLists.add(jta);
        }
    }
    //   String text = "date;ifconfig wlan1";
    String Command = "date;echo end;ifconfig;";
    @Override
    public void run() {
        String LongStr = "";
        for (int j = 0; j < CONSTANTS.ROUTERS; j++) {
            CreateLogs c = new CreateLogs(j);
            c.start();
        }
    }
}

class CreateLogs extends Thread
{
    //private static JTabbedPane tabb;
    //private ArrayList<JTextArea> logsLists = new ArrayList();
    String Command = "date;echo end;ifconfig;";
    int j;
    String LongStr = "";
   // logsLists = backGroundScripts.logsLists;
    public CreateLogs(int index){
        //this.logsLists = logsLists;
        //this.tabb = tabb;
        j = index;
    }
    @Override
    public void run(){
            String IP = RouterInfo.routerList.get(j).IP;
            String ID = RouterInfo.routerList.get(j).ID;
            try {
                System.out.println(Command);
                String RawResultl = global.InterfaceFactory.getSessioninterface().generateExecCommands(IP, Command);
                //String ParsedResult = global.InterfaceFactory.getParserinterface().resposedResult(RawResultl, IP);
                String[] ResultSplitting = RawResultl.split("end");
                String[] Time = ResultSplitting[0].split(" ");
                backGroundScripts.logsLists.get(j).append("Date : ");
                for (int k = 0; k < Time.length; k++) {
                    if (k < 3) {
                        backGroundScripts.logsLists.get(j).append(Time[k] + " ");
                        LongStr += Time[k] + "";
                    } else if (k == 4) {
                        backGroundScripts.logsLists.get(j).append("\tTime ");
                    } else {
                        backGroundScripts.logsLists.get(j).append(Time[k] + " ");
                        LongStr += "\t" + Time[k] + "";
                    }
                }
                backGroundScripts.logsLists.get(j).append("Router Id : " + ID);
                LongStr += "\t" + ID;
                LongStr += "\t" + IP;
                backGroundScripts.logsLists.get(j).append("\nRouter IP : " + IP);
                String[] newstr = ResultSplitting[1].split("\n");
                for (int i = 0; i < newstr.length; i++) {
                    //RadiosInfo obj = new RadiosInfo();
                    if (newstr[i].contains("wlan1") || newstr[i].contains("wlan0")) {
                        try {
                            int lanindex = 0;
                            lanindex = (newstr[i].contains("wlan1")) ? 0 : 1;
                            backGroundScripts.logsLists.get(j).append("\nwlan " + lanindex);
                            int index = i + 1;
                            if (newstr[index].contains("addr")) {
                                backGroundScripts.logsLists.get(j).append("\nAddress : " + newstr[index].substring(newstr[i + 1].lastIndexOf("addr") + 5, newstr[index].lastIndexOf("Bcast")));
                                LongStr += "\t" + newstr[index].substring(newstr[i + 1].lastIndexOf("addr") + 5, newstr[index].lastIndexOf("Bcast"));

                            }
                            if (newstr[index].contains("Mask")) {
                                backGroundScripts.logsLists.get(j).append("\nMask : " + newstr[i + 1].substring(newstr[index].lastIndexOf("Mask") + 5));
                                LongStr += "\t" + newstr[i + 1].substring(newstr[index].lastIndexOf("Mask") + 5);
                                index++;
                            }
                            if (newstr[index].contains("MTU")) {
                                backGroundScripts.logsLists.get(j).append("\nMTU : " + newstr[index].substring(newstr[index].lastIndexOf("MTU") + "MTU:".length(), newstr[index].lastIndexOf("Metric") - 1));
                                LongStr += "\t" + newstr[index].substring(newstr[index].lastIndexOf("MTU") + "MTU:".length(), newstr[index].lastIndexOf("Metric") - 1);
                                index++;
                            }
                            if (newstr[index].contains("RX packets")) {
                                backGroundScripts.logsLists.get(j).append("\nRX packets : " + newstr[index].substring(newstr[index].lastIndexOf("RX packets:") + "RX packets:".length(), newstr[index].lastIndexOf("errors:") - 1));
                                LongStr += "\t" + newstr[index].substring(newstr[index].lastIndexOf("RX packets:") + "RX packets:".length(), newstr[index].lastIndexOf("errors:") - 1);
                                index++;
                            }
                            if (newstr[index].contains("TX packets")) {
                                backGroundScripts.logsLists.get(j).append("\nTX packets : " + newstr[index].substring(newstr[index].lastIndexOf("TX packets:") + "TX packets:".length(), newstr[index].lastIndexOf("errors:") - 1));
                                LongStr += "\t" + "TX packets : " + newstr[index].substring(newstr[index].lastIndexOf("TX packets:") + "TX packets:".length(), newstr[index].lastIndexOf("errors:") - 1);

                                index++;
                            }
                            if (newstr[index].contains("txqueuelen")) {
                                backGroundScripts.logsLists.get(j).append("\nTX Q Length : " + newstr[index].substring(newstr[index].lastIndexOf("txqueuelen:") + "txqueuelen:".length()));
                                LongStr += "\tTX Q Length : " + newstr[index].substring(newstr[index].lastIndexOf("txqueuelen:") + "txqueuelen:".length());
                                index++;
                            }
                            // RX bytes:0 (0.0 B)  TX bytes:417592 (407.8 KiB)
                            if (newstr[index].contains("RX bytes")) 
                            {
                                backGroundScripts.logsLists.get(j).append("\nRX bytes : " + newstr[index].substring(newstr[index].lastIndexOf("RX bytes:") + "RX bytes:".length(), newstr[index].lastIndexOf("TX bytes:")));
                                backGroundScripts.logsLists.get(j).append("\nTX bytes : " + newstr[index].substring(newstr[index].lastIndexOf("TX bytes:") + "TX bytes:".length()));
                                LongStr += "\tTX Q Length : " + newstr[index].substring(newstr[index].lastIndexOf("RX bytes:") + "RX bytes:".length(), newstr[index].lastIndexOf("TX bytes:"));
                                LongStr += "\tTX Q Length : " + newstr[index].substring(newstr[index].lastIndexOf("TX bytes:") + "TX bytes:".length());
                            }
                               // logsLists.get(j).append("\n"+LongStr+"\n\n");
                            // System.err.println(s);
                            // System.err.println(ss);
                        } catch (Exception e)
                        {
                            System.err.println(e.getMessage());
                            backGroundScripts.logsLists.get(j).append(e.getMessage());
                        }
                    } else {
                        Label l = new Label("<html><body style='color:Red'>\nNo Radio Found</html></body>");
                        backGroundScripts.logsLists.get(j).add(l);
                    }
                }
                //Thread.sleep(CONSTANTS.TIMEINTERVAL_LOGS);
            } catch (Exception e) 
            {
                
            }
    }
}
class BackGroundScriptsToFile extends TimerTask {

    String Command = "uptime;echo end;ifconfig;echo end;"+SshCommand.WIRELESS_CONF+";echo ..................";
    String filename = "D://MyFile.txt";
    FileWriter fw;

    public void run() {
         Thread t = new Thread(){public void run(){RouterInfo.LiveRouterInfo();}};
        t.start();
        for (int j = 0; j < CONSTANTS.ROUTERS; j++) {
            String IP = RouterInfo.routerList.get(j).IP;
            String ID = RouterInfo.routerList.get(j).ID;
            if (RouterInfo.routerList.get(j).isAlive) {
                String RawResultl = global.InterfaceFactory.getSessioninterface().generateExecCommands(IP, Command);
                try {
                    fw = new FileWriter(filename, true); //the true will append the new data
                    fw.write("\n" + RawResultl);//appends the string to the file
                   fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(BackGroundScriptsToFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            fw = new FileWriter(filename, true); 
            fw.write("*********************");
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(BackGroundScriptsToFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
