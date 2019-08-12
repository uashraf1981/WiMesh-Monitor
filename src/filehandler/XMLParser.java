/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filehandler;

import global.CONSTANTS;
import global.RouterNode;
import java.io.File;  
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;
import meshmonitoringunit.RouterInfo;
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author said
 */
public class XMLParser {  
  public boolean ReadXML() throws ParserConfigurationException, SAXException {
        try {

            File xmlFile = new File(CONSTANTS.XmlFilePath);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentFactory
                    .newDocumentBuilder();
            org.w3c.dom.Document doc = documentBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Node");

            //System.out.println("Root element :"  + doc.getDocumentElement().getNodeName());  
            //String[][] Mesh = new String[14][3];
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                //System.out.println("\nElement type :" + node.getNodeName());  
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element Routers = (Element) node;
                    // Mesh[temp][0] = Routers.getAttribute("name");
                    RouterNode routernode = new  RouterNode();
                    routernode.ID = Routers.getElementsByTagName("RouterID").item(0).getTextContent().toString();
                    routernode.IP = Routers.getElementsByTagName("IP").item(0).getTextContent().toString();
                    routernode.Mask = Routers.getElementsByTagName("MASK").item(0).getTextContent().toString();
                    System.out.println(routernode.IP + "   " + routernode.ID + "  "+ routernode.Mask);
                    RouterInfo.routerList.add(routernode);
//     System.out.println("name  : "  
//       + Routers.getAttribute("name"));  
//     System.out.println("IP : "  
//       + Routers.getElementsByTagName("IP").item(0)  
//         .getTextContent());  
//     System.out.println("MASK : "  
//       + Routers.getElementsByTagName("MASK").item(0)  
//         .getTextContent());  
                }
            }
            return true;
        } catch (IOException ex) {
            return false;

        }
    }
} 