/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshmonitoringunit;

import global.TextAreaOutputStream;
import gui.WiMeshMainForm;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import meshcommunicationunit.Channel;

/**
 *
 * @author ICTWimesh
 */
public class Logging {
    public static boolean isClicked = false;
    
    public static Hashtable<String,String[]> RoutersFreeMemory = new Hashtable<String,String[]>();
    public void StoreRoutersFreeMemory(String[] FreeMemory,String IP){
    //RouterInfo.RoutingInfo.put(IP,new String[]{"***", "***", "***", "***",IP, "***", "***", "***"});
      //  for(int i = 3;i<RoutingDb.length;i++){
            Logging.RoutersFreeMemory.put(IP,FreeMemory);
       // }
    }
//    public void openStreamForrouter(int i,JTextArea jta){
//               PipedOutputStream out = null;
//                try {
//                    //RouterInfo obj = new RouterInfo();
////            if(!ProcessInfo.isChecked){
////                //LogsTextArea
////            }
//                    Channel.channelShell[i].setOutputStream(new TextAreaOutputStream(jta));
//                    global.CONSTANTS.pin[i].write(("dmesg\n").getBytes());
//                  //  global.InterfaceFactory.getProcessinterface().showProcessInfo(ProcessingTable);
//                     isClicked = true;
//                } catch (IOException ex) {
//                    Logger.getLogger(WiMeshMainForm.class.getName()).log(Level.SEVERE, null, ex);
//                } finally {
//                    try {
//                        out.close();
//                    } catch (IOException ex) {
//                        Logger.getLogger(WiMeshMainForm.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//               
//    }
    
}
