/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshcontroller;

import global.InterfaceFactory;
import gui.WiMeshMainForm;
import static gui.WiMeshMainForm.out_;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import meshcommunicationunit.Channel;
import meshmonitoringunit.RouterInfo;

/**
 *
 * @author ICTWimesh
 */
public class Backup {
    public String TakeAllBackUP(String filename,String FileToBeDownload,int index,String desctinationFolder) throws IOException{
                  String filesToCompress = "cd ../;tar -cvf " + filename + " " + FileToBeDownload;
                           // int index = BackUp_RouterList.getSelectedIndex() - 1;
                            Channel.channelShell[index].setOutputStream(out_[index]);
                            global.CONSTANTS.pin[index].write((filesToCompress + "\n").getBytes());
                            //Channel.channelShell[BackUp_RouterList.getSelectedIndex()].setOutputStream(new PrintStream(new TextAreaOutputStream(LogsTextArea)));
                            //global.CONSTANTS.pin[BackUp_RouterList.getSelectedIndex()].write((filesToCompress+"\n").getBytes());
                            String IP = RouterInfo.routerList.get(index).IP;
                          
                      
                                try {
                                    final String DownloadStatus = InterfaceFactory.getFileinterface().download(filename, IP, desctinationFolder);
                                   global.CONSTANTS.pin[index].write(("cd ../;rm " + filename + " " + "\n").getBytes());
                                    final String DownloadStatus_ = (DownloadStatus.isEmpty()) ? "Sucessfully Downloaded " : DownloadStatus;
                                   return(DownloadStatus_);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(WiMeshMainForm.class.getName()).log(Level.SEVERE, null, ex);
                                    return null;
                                }
    }
    
    public String RouterBackUp(String filename,String FileToBeDownload,int index,String IP,String desctinationFolder) throws IOException, InterruptedException{
         String filesToCompress = "cd ../;tar -cvf " + filename + " " + FileToBeDownload;
               // int index = BackUp_RouterList.getSelectedIndex() - 1;
                Channel.channelShell[index].setOutputStream(out_[index]);
                global.CONSTANTS.pin[index].write((filesToCompress + "\n").getBytes());
                //Channel.channelShell[BackUp_RouterList.getSelectedIndex()].setOutputStream(new PrintStream(new TextAreaOutputStream(LogsTextArea)));
                //global.CONSTANTS.pin[BackUp_RouterList.getSelectedIndex()].write((filesToCompress+"\n").getBytes());
                String DownloadStatus = InterfaceFactory.getFileinterface().download(filename, IP, desctinationFolder);
                global.CONSTANTS.pin[index].write(("cd ../;rm " + filename + " " + "\n").getBytes());
                DownloadStatus = (DownloadStatus.isEmpty()) ? "Sucessfully Downloaded " : DownloadStatus;
                return DownloadStatus;
    }
}
