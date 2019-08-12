/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

import gui.WiMeshMainForm;
import static gui.WiMeshMainForm.RouterStatusInfo;
import java.util.Hashtable;
import meshmonitoringunit.ProcessInfo;  
import meshmonitoringunit.RouterInfo;

/**
 *
 * @author ICTWimesh
 */
public class Parser {
    //07:16:09 up 12 days, 21:43,  3 users,  load average: 0.00, 0.01, 0.05
    static int nidex = 0;
    public  String resposedResult(String RouterResponse,String IP,int index){
       // System.out.println(uptime);
       System.out.println("**************************");
       String EliminateSpaces = eleminateEmptySpace(RouterResponse);
       System.out.println(EliminateSpaces);
       System.out.println("**************************");
      //08:56:06 up 12 days, 23:23, 8 users, load average: 0.00, 0.01, 0.05 Tue Mar 11 08:56:06 UTC 2014
       String[] ArrayDb = EliminateSpaces.split("end");
       // Elemintae UPTIME
//       String[] RouterResponce = new String[]{"uptimedb","RoutingDb","ProcessesDb","freememDb","ConfigDb","ifConfigDb"};
//        Hashtable<String, String[]> ArrayList = new Hashtable<String, String[]>();
//        int whichOne = 0;
//       for(int i=0;i<ArrayDb.length;i++){
//           if(i ==0){
//               if(ArrayDb[i].startsWith(";")){
//                   continue;
//               }
//               else{
//                   ArrayList.put(RouterResponce[whichOne], ArrayDb[i]);
//                   whichOne++;
//               }
//           }
//       }
       
        String[] uptimedb = ArrayDb[0].split(",");
        String[] RoutingDb = ArrayDb[1].split("\n");
        String[] ProcessesDb = ArrayDb[2].split("\n");
        
        String[] remProcess = ArrayDb[ArrayDb.length -4].split("\n");
        String[] freememDb = ArrayDb[ArrayDb.length -3].split("\n")[2].split(" ");
        String[] ConfigDb = ArrayDb[ArrayDb.length -2].replace(" ", "'").split(",");
        String[] ifConfigDb = ArrayDb[ArrayDb.length -1].split("\n");
        
       // global.InterfaceFactory.getProcessinterface().storeProcessinfo(ProcessesDb, IP);
        global.InterfaceFactory.getProcessinterface().storeProcessinfo(ProcessesDb,index,remProcess);
        global.InterfaceFactory.getRouterinterface().StoreRoutingInfo(RoutingDb,IP);
        global.InterfaceFactory.getRouterinterface().storeRadioInfo(ifConfigDb,ConfigDb, IP,index);
        global.InterfaceFactory.getlogsinterface().StoreRoutersFreeMemory(freememDb, IP);
       
       String Uptime = uptimedb[0];
       String AvgLoad = uptimedb[1];
       // Getting Routing Table
       // Getting Processes Info
        String freem = ArrayDb[6];
       // RouterStatusInfo[nidex].setToolTipText("<html><body style='width=300px;height=450;border:1px solid #ccc'>"+freem+"</body></html>");
       // nidex++;
        return Uptime +"<br/>"+ AvgLoad;
    }
    
    public  String eleminateEmptySpace(String responcedMessage) {
      return responcedMessage.replaceAll("^\\s+|\\s+$|\\s*(\n)\\s*|(\\s)\\s*", "$1$2").replace("\t"," ");
      //  return "";
    }
    
    public String parse_backGroundScript(String rawString){
        
        System.out.println(rawString);
        
        return null;
    }
  

}
