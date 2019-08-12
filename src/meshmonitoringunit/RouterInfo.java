/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshmonitoringunit;

import global.CONSTANTS;
import global.DrawPieChart;
import global.EthObject;
import static global.EthObject.ETHList;
import global.RadiosInfo;
import global.RouterNode;
import global.SshCommand;
import gui.WiMeshMainForm;
import static gui.WiMeshMainForm.RouterETHInfo;
import static gui.WiMeshMainForm.RouterRadio0Info;
import static gui.WiMeshMainForm.RouterRadio1Info;
import static gui.WiMeshMainForm.RouterStatusInfo;
import java.awt.Color;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import meshcommunicationunit.RegenerateSession;

/**
 *
 * @author ICTWimesh
 */
public class RouterInfo {
    public static boolean isChecked = false;
    public static LinkedList<RouterNode> routerList = new LinkedList<RouterNode>();
    public static String[] NodesInfo = new String[CONSTANTS.ROUTERS];
    public static Hashtable<String,String[]> RoutingInfo = new Hashtable<String,String[]>();
    public static DefaultTableModel model;
    public static final  JTable[] jt2 = new JTable[CONSTANTS.ROUTERS];
    
    public void showRouteInfo(JTabbedPane jtp) {
            jtp.removeAll();
                  String[] columnNames = {"",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",};
                  
                  for(int i=0;i<routerList.size();i++){
                  try 
                  {
                  String IP = RouterInfo.routerList.get(i).IP;
                  String[] myvaluesDB = RoutingInfo.get(IP);
                  Object[][] data = new String[myvaluesDB.length][8];
                      for (int j = 0; j < RoutingInfo.get(IP).length-2;j++) {
                        // String[] myvalues = myvaluesDB[i].split(" ");
                            String[] myvalues = RoutingInfo.get(IP)[j+2].split(" ");//.split(" ");
                           // for (int k =3; k < myvalues.length;k++) {
                             data[j][0] = myvalues[0];
                             data[j][1] = myvalues[1];
                             data[j][2] = myvalues[2];
                             data[j][3] = myvalues[3];
                             data[j][4] = myvalues[4];
                             data[j][5] = myvalues[5];
                             data[j][6] = myvalues[6];
                             data[j][7] = myvalues[7]; //, myvalues[1].toString(), myvalues[2], myvalues[3],myvalues[4],myvalues[5]});
                           // }  
                  }
                      final int s = i;
                 // final  JTable jt = new JTable(data,columnNames);
                      
                      jt2[i] = new JTable(data,columnNames);
                      jtp.add(RouterInfo.routerList.get(i).IP, jt2[i]);
                      
//                  jt2[i].addMouseListener(new java.awt.event.MouseAdapter() {
//                    public void mouseReleased(java.awt.event.MouseEvent evt) 
//                    {
//                        WiMeshMainForm.btnKillProcess.setEnabled(false);
//                        String PID = InterfaceFactory.getProcessinterface().getPID(jt[s]);
//                        System.out.print(PID);
//                        if(PID != "" && PID != "0"){
//                        WiMeshMainForm.btnKillProcess.setEnabled(true);
//                        }
//                    }
//                  });

                        }
                  catch (Exception e) {
            System.out.println("Error" + e.getMessage());
                    }
                  }
         ProcessInfo.isChecked = true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void showRoutingInfo(JTable routingTableInfo) {
        try {
         //   if(routingTableInfo.getRowCount() == 1){
            model = (DefaultTableModel) routingTableInfo.getModel();
          //  model.insertRow(1, );
            for (int i = 1; i < RouterInfo.RoutingInfo.size(); i++) {
                String[] myvalues = RoutingInfo.get(i);
                if(myvalues.length==8){
                 model.insertRow(1, new Object[]{myvalues[0], myvalues[1], myvalues[2], myvalues[3], myvalues[4], myvalues[5], myvalues[6], myvalues[7]});
                }
            }
           // }
            RouterInfo.isChecked = true;
        } catch (Exception e) {
            RouterInfo.isChecked = true;
            System.out.println("Error" + e.getMessage());
        }
    }
    
 public void StoreRoutingInfo(String[] RoutingDb,String IP){
    //RouterInfo.RoutingInfo.put(IP,new String[]{"***", "***", "***", "***",IP, "***", "***", "***"});
      //  for(int i = 3;i<RoutingDb.length;i++){
            RouterInfo.RoutingInfo.put(IP,RoutingDb);
       // }
    }
 public void storeRadioInfo(String[] newstr, String[] ConfigurationDb,String IP,int index_){
 boolean iswLan0 = false;
 boolean iswLan1 = false;
 
for(int i=0;i<newstr.length;i++){
     RadiosInfo obj = new RadiosInfo();
         if(newstr[i].contains("wlan1") || newstr[i].contains("wlan0"))
            {
                try
                {
                    int index=i+1;
                    if(newstr[index].contains("addr")){
                        obj.setRadioIpAddress( newstr[index].substring(newstr[i+1].lastIndexOf("addr")+5,newstr[index].lastIndexOf("Bcast")));
                    }
                    if(newstr[index].contains("Mask")){obj.setMask(newstr[i+1].substring(newstr[index].lastIndexOf("Mask")+5));
                  //  WiMeshMainForm.WireLess_ConfigR0Old[index_][1].setText(newstr[i+1].substring(newstr[index].lastIndexOf("Mask")+5));
                    index++;}
                    if(newstr[index].contains("MTU")){
                        obj.setStatus(newstr[index].split(" ")[0]);
                        obj.setMtu(newstr[index].substring(newstr[index].lastIndexOf("MTU")+"MTU:".length(),newstr[index].lastIndexOf("Metric")-1));
                        index++;
                    }
                    if(newstr[index].contains("RX packets")){obj.setRxPackets(newstr[index].substring(newstr[index].lastIndexOf("RX packets:")+"RX packets:".length(),newstr[index].lastIndexOf("errors:")-1));index++;}
                    if(newstr[index].contains("TX packets")){obj.setTxPackets(newstr[index].substring(newstr[index].lastIndexOf("TX packets:")+"TX packets:".length(),newstr[index].lastIndexOf("errors:")-1));index++;}
                    if(newstr[index].contains("txqueuelen")){obj.setTxQLength(newstr[index].substring(newstr[index].lastIndexOf("txqueuelen:")+"txqueuelen:".length()));}
                    if(newstr[i].contains("wlan1"))
                    { 
                        try{
                        WiMeshMainForm.WireLess_ConfigR0Old[index_][0].setText(obj.getRadioIpAddress());
                        WiMeshMainForm.WireLess_ConfigR0Old[index_][1].setText(obj.getMask());
                        WiMeshMainForm.WireLess_ConfigR0Old[index_][2].setText(ConfigurationDb[2]);
                        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
                        
                       WiMeshMainForm.WireLess_ConfigR0Old[index_][0].setBorder(border);
                       WiMeshMainForm.WireLess_ConfigR0Old[index_][1].setBorder(border);
                       WiMeshMainForm.WireLess_ConfigR0Old[index_][2].setBorder(border);

                       WiMeshMainForm.WireLess_ConfigR0Old[index_][0].setEditable(false);
                       WiMeshMainForm.WireLess_ConfigR0Old[index_][1].setEditable(false);
                       WiMeshMainForm.WireLess_ConfigR0Old[index_][2].setEditable(false);

                        obj.setMode(ConfigurationDb[0]);
                        obj.setSsid(ConfigurationDb[1]);
                        obj.setChannel(ConfigurationDb[2]);
                        obj.setRouterIpAddress(IP);//Radio1
                        RadiosInfo.Radios1List.put(IP,obj);
                        iswLan1 = true;
                        }
                        catch(Exception e){
                            RadiosInfo.Radios1List.put(IP,null);
                        }
                    }
                    else
                    { 
                        try{
                        WiMeshMainForm.WireLess_ConfigR1Old[index_][0].setText(obj.getRadioIpAddress());
                        WiMeshMainForm.WireLess_ConfigR1Old[index_][1].setText(obj.getMask());
                        WiMeshMainForm.WireLess_ConfigR1Old[index_][2].setText(ConfigurationDb[5]);
                        
                         Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                       WiMeshMainForm.WireLess_ConfigR1Old[index_][0].setBorder(border);
                       WiMeshMainForm.WireLess_ConfigR1Old[index_][1].setBorder(border);
                       WiMeshMainForm.WireLess_ConfigR1Old[index_][2].setBorder(border);
                       WiMeshMainForm.WireLess_ConfigR1Old[index_][0].setEditable(false);
                       WiMeshMainForm.WireLess_ConfigR1Old[index_][1].setEditable(false);
                       WiMeshMainForm.WireLess_ConfigR1Old[index_][2].setEditable(false);
                        obj.setMode(ConfigurationDb[3]);
                        obj.setSsid(ConfigurationDb[4]);
                        obj.setChannel(ConfigurationDb[5]);
                        obj.setRouterIpAddress(IP); //Radio0
                        RadiosInfo.Radios0List.put(IP,obj);
                        iswLan0 = true;
                    }
                        catch(Exception e){
                            RadiosInfo.Radios1List.put(IP,null);
                         }
                    }
                   // System.err.println(s);
                  // System.err.println(ss);
                }
                catch(Exception e)
                { 
                    
                       System.err.println(e.getMessage()); 
                }
                //varibleForWireLess_Config++;
            }
          if(newstr[i].contains("eth0.1"))
            {
                try
                {
                    EthObject obj_ = new EthObject();
                    int index=i+1;
                    if(newstr[index].contains("addr")){
                        obj_.setETHIP(newstr[index].substring(newstr[i+1].lastIndexOf("addr")+5,newstr[index].lastIndexOf("Bcast")));
                    }
                    if(newstr[index].contains("Mask")){obj_.setMask(newstr[i+1].substring(newstr[index].lastIndexOf("Mask")+5));
                   index++;}
                    if(newstr[index].contains("MTU")){
                        obj_.setStatus(newstr[index].split(" ")[0]);
                        obj_.setMtu(newstr[index].substring(newstr[index].lastIndexOf("MTU")+"MTU:".length(),newstr[index].lastIndexOf("Metric")-1));
                        index++;
                    }
                    if(newstr[index].contains("RX packets")){obj_.setRxPackets(newstr[index].substring(newstr[index].lastIndexOf("RX packets:")+"RX packets:".length(),newstr[index].lastIndexOf("errors:")-1));index++;}
                    if(newstr[index].contains("TX packets")){obj_.setTxPackets(newstr[index].substring(newstr[index].lastIndexOf("TX packets:")+"TX packets:".length(),newstr[index].lastIndexOf("errors:")-1));index++;}
                    if(newstr[index].contains("txqueuelen")){obj_.setTxQLength(newstr[index].substring(newstr[index].lastIndexOf("txqueuelen:")+"txqueuelen:".length()));}
                    ETHList.put(IP,obj_);

                }
                catch(Exception e){
                    ETHList.put(IP,new EthObject());
                }
            }
    } 
    if(!iswLan0){
        RadiosInfo obj = new RadiosInfo();
            obj.setStatus("DOWN");
            RadiosInfo.Radios0List.put(IP,obj);
    } if(!iswLan1){
        RadiosInfo obj = new RadiosInfo();
            obj.setStatus("DOWN");
            RadiosInfo.Radios1List.put(IP,obj);
    }
 }
 
 public void parseIfcongDataForLogging(String[] newstr, String IP){
for(int i=0;i<newstr.length;i++){
     RadiosInfo obj = new RadiosInfo();
         if(newstr[i].contains("wlan1") || newstr[i].contains("wlan0"))
            {
                try
                {
                    int index=i+1;

                   // System.err.println(s);
                  // System.err.println(ss);
                }
                catch(Exception e)
                { 
                       System.err.println(e.getMessage()); 
                }
            }
    } 
 }
//    public String getPID(JTable routingTableInfo){
//        String PID = String.valueOf(model.getValueAt(routingTableInfo.getSelectedRows()[0], 0));
//        return PID;
//    }
 
 public static void LiveRouterInfo(){
     for (int i = 0; i < CONSTANTS.ROUTERS; i++) {
            try {
                String IP = RouterInfo.routerList.get(i).IP;
                String ID = RouterInfo.routerList.get(i).ID;
                String MASK = RouterInfo.routerList.get(i).Mask;
                Boolean isAlive = RouterInfo.routerList.get(i).isAlive;
                 if (isAlive) {
                    System.out.println("Alive : " + RouterInfo.routerList.get(i).IP);
                             //   RouterStatusInfo[i].setText();
                    RouterStatusInfo[i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    String Command = SshCommand.NODE_UP_TIME + SshCommand.ECHO + SshCommand.ROUTING_TABLE + SshCommand.ECHO + SshCommand.PROCESSING_INFO + SshCommand.ECHO + SshCommand.FREE_MEMORY + SshCommand.ECHO + SshCommand.WIRELESS_CONF + SshCommand.ECHO + "ifconfig " + SshCommand.ECHO;
                    System.out.println(Command);
                    RouterInfo.NodesInfo[i] = global.InterfaceFactory.getSessioninterface().generateExecCommands(IP, Command);
                    
                    DrawPieChart.DrawCharts(global.InterfaceFactory.getlogsinterface().RoutersFreeMemory.get(IP)[1], global.InterfaceFactory.getlogsinterface().RoutersFreeMemory.get(IP)[2], i);
                    if (RouterInfo.NodesInfo[i] != null) 
                    {
                        String str = global.InterfaceFactory.getParserinterface().resposedResult(RouterInfo.NodesInfo[i], IP, i);
                        RouterStatusInfo[i].setText("<html><body style='font-size:8px'> IP : " + IP + " <br/> ID : " + ID + "<br/> " + "Uptime: " + str + "<br/></body></html>");
                        if (RadiosInfo.Radios0List.get(IP)!= null) 
                        {
                            if (RadiosInfo.Radios0List.get(IP).getStatus().contains("UP")) {
                                RouterRadio0Info[i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                                RouterRadio0Info[i].setText("<html><body style='font-size:6px'>Mode: " + RadiosInfo.Radios0List.get(IP).getMode() + "<br/> SSID:" + RadiosInfo.Radios0List.get(IP).getSsid() + "<br/>Traffic: " + RadiosInfo.Radios0List.get(IP).getTxPackets() + " / " + RadiosInfo.Radios0List.get(IP).getRxPackets() + "</html></body>");
                            }
                            else 
                            {
                                RouterRadio0Info[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                            }
                        }
                        else 
                        {
                            RouterRadio0Info[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                            RouterRadio0Info[i].setText("Not Responding");
                        }
                        if (RadiosInfo.Radios1List.get(IP) != null) {
                            if (RadiosInfo.Radios1List.get(IP).getStatus().contains("UP")) {
                                RouterRadio1Info[i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                                RouterRadio1Info[i].setText("<html><body style='font-size:6px'>Mode: " + RadiosInfo.Radios1List.get(IP).getMode() + "<br/> SSID:" + RadiosInfo.Radios1List.get(IP).getSsid() + "<br/>Traffic: " + RadiosInfo.Radios1List.get(IP).getTxPackets() + " / " + RadiosInfo.Radios1List.get(IP).getRxPackets() + "</html></body>");
                            } else {
                                RouterRadio1Info[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                                RouterRadio1Info[i].setText("Not Responding");
                            }
                        } else {
                            RouterRadio1Info[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                            RouterRadio1Info[i].setText("Not Responding");
                        }
                    }
                    RouterETHInfo[i].setText("<html><body style='font-size:6px'>IP: " + ETHList.get(IP).getETHIP() + "<br/> MASK:" + ETHList.get(IP).getMask() + "<br/>Traffic: " + ETHList.get(IP).getTxPackets() + " / " + ETHList.get(IP).getRxPackets() + "</html></body>");
                  //  CONSTANTS.ALIVE_ROUTERS++;
                    //   networkStaustf.append("\nAlive Node:" + CONSTANTS.ALIVE_ROUTERS);
                } else {
                    //    System.out.println("Dead : " + RouterInfo.routerList.get(i).IP);
                     
                        RouterRadio1Info[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                        RouterRadio0Info[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                        RouterInfo.routerList.get(i).isAlive = false;
                        RegenerateSession mt = new RegenerateSession (i,IP);
                        mt.start();
                        //global.InterfaceFactory.getSessioninterface().reGenerateSession(index, IP_);
                        
                    RouterStatusInfo[i].setText("<html><body style='font-size:8px'> IP : " + IP + " <br/> ID : " + ID + "<br/></body></html>");
                    RouterStatusInfo[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                    
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\n" + e.getStackTrace());
            }
        }
 }
 
 public void clearRouterCommandinterface(){
           for(int index=0;index<CONSTANTS.ROUTERS;index++){
                    int sp = WiMeshMainForm.consoles_[index].getText().lastIndexOf("root@OpenWrt:~#");
                    int ep = WiMeshMainForm.consoles_[index].getText().length();
                    String s = WiMeshMainForm.consoles_[index].getText().substring(sp,ep);
                    WiMeshMainForm.consoles_[index].setText(s);
          }
 }
}
