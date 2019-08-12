/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filehandler;

import javax.xml.parsers.ParserConfigurationException;
import meshcommunicationunit.Session;
import org.xml.sax.SAXException;

/**
 *
 * @author ICTWimesh
 */
public class Boot {
     /*
        1. Parse the MasterList.xml and note the total number of nodes in the network, their IDs and their IP Addresses
        2. Open SSH connections to all the routers mentioned in the MasterList.xml file. If some of them are not responding then show their identities 
        3. Check that the Router ID, IP and Mask in the WiMesh.info match those in the MasterList.xml if not then display those routers/entries
        4. For each router, update the /etc/config/network and /etc/config/wireless files which contain the interface (Wireless or Wired NICs) according 
           to the information contained in the WiMesh.info file
        5. Reboot the routers
        6. Re-open SSH connections to the Routers. If some are not responding show their identities
        7. Check that the Router ID, IP and Mask currently assigned to the router matches the info in their WiMesh.info file. 
        8. Check if Router RAM/ROM memory nearly full (90%), issue warning for the nodes that have
        9. Show System OK message!
        */
    public boolean initBoot() throws ParserConfigurationException, SAXException, InterruptedException{
        XMLParser obj = new XMLParser();
        if(obj.ReadXML())
        {
            Thread t = new Thread(){
                public void run(){
                    meshcommunicationunit.Session session = new meshcommunicationunit.Session();
                    session.generateShellSessions(); 
                }};
            t.start();
            t.join();
            return true;
        }
          return false;
    }
}
