/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshmonitoringunit;

import global.CONSTANTS;
import global.InterfaceFactory;
import global.SshCommand;
import gui.WiMeshMainForm;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import meshcommunicationunit.Session;

/**
 *
 * @author ICTWimesh
 */
public class ProcessInfo{
    public static boolean isChecked = false;
    //public static LinkedList<String[]> ProcessInfodb = new LinkedList<String[]>();
        public static Hashtable<Integer,String[]> ProcessInfodb    = new Hashtable< Integer,String[]>();
        public static Hashtable<Integer,String[]> RemProcessInfodb    = new Hashtable< Integer,String[]>();

    public static DefaultTableModel model;
    public static final  JTable[] jt = new JTable[CONSTANTS.ROUTERS];

       public void showProcessInfo(JTable ProcessTableInfo) {
       // try {
          //  if(ProcessTableInfo.getRowCount()==0){
            model = (DefaultTableModel) ProcessTableInfo.getModel();
            for (int i = 1; i < ProcessInfo.ProcessInfodb.size(); i++) {
                String[] myvalues = ProcessInfo.ProcessInfodb.get(i);
              //  if(myvalues.length == 5){
                    model.insertRow(1, new Object[]{myvalues[0], myvalues[1].toString(), myvalues[2], myvalues[3],myvalues[4],myvalues[5]});
               // }
                }
          //  }
            ProcessInfo.isChecked = true;
       // } catch (Exception e) {
        //    System.out.println("Error" + e.getMessage());
       // }
    }
   
       public void showProcessInfo(JTabbedPane jtp) {
        try {
            jtp.removeAll();
                  String[] columnNames = {"",
                            "",
                            "",
                            "",
                            ""};
                  for(int i=0;i<ProcessInfodb.size();i++){
                   //   try{
                  Object[][] data = new String[ProcessInfodb.get(i).length+RemProcessInfodb.get(i).length][5];
                      for (int j = 0; j < ProcessInfo.ProcessInfodb.get(i).length-1;j++) {
                         String[] myvalues = ProcessInfo.ProcessInfodb.get(i)[j+1].split(" ");//.split(" ");
                           // for (int k = 0; k < myvalues.length;k++) {
                             data[j][0] = myvalues[0];
                             data[j][1] = myvalues[1];
                             data[j][2] = myvalues[2];
                             data[j][3] = myvalues[3];
                             data[j][4] = myvalues[4];//, myvalues[1].toString(), myvalues[2], myvalues[3],myvalues[4],myvalues[5]});
                           // }  
                  }
                      
//                      for (int k = 0;k<RemProcessInfodb.get(i).length;k++) 
//                      {
//                            String[] myvalues = ProcessInfo.RemProcessInfodb.get(i)[k+1].split(" ");//.split(" ");
//                            // for (int k = 0; k < myvalues.length;k++) {
//                             data[ProcessInfo.ProcessInfodb.get(i).length+k][0] = myvalues[0];
//                             data[ProcessInfo.ProcessInfodb.get(i).length+k][1] = myvalues[1];
//                             data[ProcessInfo.ProcessInfodb.get(i).length+k][2] = myvalues[2];
//                             data[ProcessInfo.ProcessInfodb.get(i).length+k][3] = myvalues[3];
//                             data[ProcessInfo.ProcessInfodb.get(i).length+k][4] = myvalues[4];//, myvalues[1].toString(), myvalues[2], myvalues[3],myvalues[4],myvalues[5]});
//                           // }  
//                    }
                   
                      final int s = i;
                 // final  JTable jt = new JTable(data,columnNames);
                      jt[i] = new JTable(data,columnNames);
                      jtp.add(RouterInfo.routerList.get(i).IP, jt[i]);
                  jt[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseReleased(java.awt.event.MouseEvent evt) 
                    {
                        WiMeshMainForm.btnKillProcess.setEnabled(false);
                        String PID = InterfaceFactory.getProcessinterface().getPID(jt[s]);
                        System.out.print(PID);
                        if(PID != "" && PID != "0"){
                        WiMeshMainForm.btnKillProcess.setEnabled(true);
                        }
                    }
                  });
                  }
                  
//                    for(JTable jtbl : jt)//int i=ProcessInfo.ProcessInfodb.get(i).length;i<ProcessInfo.ProcessInfodb.get(i).length+RemProcessInfodb.size();i++){
//                    {
//                        
//                    }
                  
                  //    catch(Exception e){}
                 //}
        }
        catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
         ProcessInfo.isChecked = true;
    }


       public String getPID(JTable routingTableInfo){
        try{
            int PID[] =  routingTableInfo.getSelectedRows();//
            //String.valueOf(model.getValueAt(routingTableInfo.getSelectedRow(),0));
             //routingTableInfo.getValueAt(routingTableInfo.getSelectedRows(),0);
            String Pids = "";
            if(PID.length > 1){
                for(int id : PID){
                    String pi = routingTableInfo.getValueAt(id,0).toString();
                    Pids += pi + " ";
                    
                }
            }
            else{
                String pi = routingTableInfo.getValueAt(routingTableInfo.getSelectedRow(),0).toString();
                Pids += pi + " ";
            }
            System.out.println("\nPIDs "+ Pids);
            return Pids;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
       }
       //Removed
       public String getIP(JTable routingTableInfo){
        try{
            String IP = String.valueOf(model.getValueAt(routingTableInfo.getSelectedRow(),0));
            System.out.println("IP "+ IP);
            return IP;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
       }
       public String getVSZ(JTable routingTableInfo){
        try{
            String VSZ = String.valueOf(model.getValueAt(routingTableInfo.getSelectedRow(),2));
            System.out.println("VSZ "+ VSZ);
            return VSZ;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
       }
       
        public void killProcess(String IP,String PID){
            String Command = SshCommand.KILL_PROCESS+" "+PID+"";
              global.InterfaceFactory.getSessioninterface().generateExecCommands(IP,Command);
        }  
        public void removeRow(JTable routingTableInfo){
          //routingTableInfo.remove(routingTableInfo.getSelectedRow());
        try{
                routingTableInfo.remove(routingTableInfo.getSelectedRow());
            }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        }
        
        public int[] getSelectedRows(JTable routingTableInfo){
        int[] selection = routingTableInfo.getSelectedRows();
        return selection;
        }
        
   public  void storeProcessinfo(String[] ProcessesDb,String IP){
      
        for(int i = 1;i<ProcessesDb.length;i++){
            String[] newRow = new String[6];
            newRow[0] = ProcessesDb[i].split(" ")[0];
            newRow[1] = ProcessesDb[i].split(" ")[1];
            newRow[2] = ProcessesDb[i].split(" ")[2];
            newRow[3] = ProcessesDb[i].split(" ")[3];
            newRow[4] = ProcessesDb[i].split(" ")[4];
            newRow[5] = IP;
            //ProcessInfo.ProcessInfodb.put(newRow);
        }
   }
   public void storeProcessinfo(String[] ProcessesDb,int index,String[] remProcess){
            ProcessInfo.ProcessInfodb.put(index, ProcessesDb);
            ProcessInfo.RemProcessInfodb.put(index, remProcess);
 
      // }
   }
}
