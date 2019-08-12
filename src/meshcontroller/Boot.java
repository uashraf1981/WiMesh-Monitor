/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshcontroller;

import global.SshCommand;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import meshcommunicationunit.RegenerateSession;
import meshcommunicationunit.Session;
import meshmonitoringunit.RouterInfo;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ICTWimesh
 */
public class Boot {
     public void bootAll() throws InterruptedException{
          Thread t = new Thread(){
                        public void run(){
                        for(int i=0;i<RouterInfo.routerList.size();i++){
                        try {
                            global.InterfaceFactory.getSessioninterface().generateExecCommands(RouterInfo.routerList.get(i).IP,SshCommand.REBOOT);
                            RouterInfo.routerList.get(i).isAlive = false;
                            RegenerateSession mt = new RegenerateSession (i,RouterInfo.routerList.get(i).IP);
                            mt.start();
                            //global.InterfaceFactory.getSessioninterface().reGenerateSession(i, RouterInfo.routerList.get(i).IP);
                        } catch (Exception ex) {
                            Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }}};
          t.start();
     }
     public  void Boot(String IP,int index) throws IOException, Exception{
            global.InterfaceFactory.getSessioninterface().generateExecCommands(IP,SshCommand.REBOOT);
            RouterInfo.routerList.get(index).isAlive = false;
            Thread.sleep(30000);
             //global.InterfaceFactory.getSessioninterface().reGenerateSession(index, IP);
            RegenerateSession mt = new RegenerateSession (index,IP);
            mt.start();
     }
}
