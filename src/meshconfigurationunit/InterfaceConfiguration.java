/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshconfigurationunit;

import global.CONSTANTS;
import global.SshCommand;
import gui.WiMeshMainForm;
import static gui.WiMeshMainForm.WireLess_ConfigR0New;
import static gui.WiMeshMainForm.WireLess_ConfigR1New;
import javax.swing.JOptionPane;
import meshmonitoringunit.RouterInfo;

/**
 *
 * @author ICTWimesh
 */
public class InterfaceConfiguration {
   public void SaveAllInterfaces(){
   for(int index=0;index<CONSTANTS.ROUTERS;index++){
           String Qry="";
          
            if (!WireLess_ConfigR0New[index][0].getText().equals("")) {
                String Radio0 = SshCommand.SET_WIFI_R0_IP + "=" + WiMeshMainForm.WireLess_ConfigR0New[index][0].getText();
                Qry += Radio0 + ";";
            }
            if (!WireLess_ConfigR1New[index][0].getText().equals("") ) {
                String Radio1 = SshCommand.SET_WIFI_R1_IP + "=" + WireLess_ConfigR1New[index][0].getText();
                Qry += Radio1 + ";";
            }
            
            if (!WireLess_ConfigR0New[index][1].getText().equals("")) {
                String mask0 = SshCommand.SET_WIFI_R0_MASK + "=" + WireLess_ConfigR0New[index][1].getText();
                Qry += mask0 + ";";
            }
            if (!WireLess_ConfigR1New[index][1].getText().equals("") ) {
                String mask1 = SshCommand.SET_WIFI_R1_MASK + "=" + WireLess_ConfigR1New[index][1].getText();
                Qry += mask1 + ";";
            }
            
            if (!WireLess_ConfigR1New[index][2].getText().equals("")) {
                String channel0 = SshCommand.SET_WIFI_R0_CHANNEL + "=" + WireLess_ConfigR0New[index][2].getText();
                Qry += channel0 + ";";
            }

            if (!WireLess_ConfigR1New[index][2].getText().equals("")) {
                String channel1 = SshCommand.SET_WIFI_R1_CHANNEL + "=" + WireLess_ConfigR1New[index][2].getText();
                Qry += channel1 + ";";
            }
            if(Qry != "")
            {      
                    String IP = RouterInfo.routerList.get(index).IP;
                    global.InterfaceFactory.getSessioninterface().generateExecCommands(IP,Qry);
            }
                }
   }
    public void changeNetworkConfig(String IP,int index ){
        try{
            String Qry="";
            if(WireLess_ConfigR0New[index][0].getText() == "" && WireLess_ConfigR0New[index][1].getText() == "" && WireLess_ConfigR0New[index][2].getText() == "" && 
                    WireLess_ConfigR1New[index][0].getText() == "" && WireLess_ConfigR1New[index][1].getText() == "" && WireLess_ConfigR1New[index][2].getText() == ""){
                JOptionPane.showMessageDialog(WiMeshMainForm.interfacePnl,"Kindly Fill the Fields");
            }
            if (!WireLess_ConfigR0New[index][0].getText().equals("")) {
                String Radio0 = SshCommand.SET_WIFI_R0_IP + "=" + WireLess_ConfigR0New[index][0].getText();
                Qry += Radio0 + ";";
            }
            if (!WireLess_ConfigR1New[index][0].getText().equals("") ) {
                String Radio1 = SshCommand.SET_WIFI_R1_IP + "=" + WireLess_ConfigR1New[index][0].getText();
                Qry += Radio1 + ";";
            }
            
             if (!WireLess_ConfigR0New[index][1].getText().equals("")) {
                String mask0 = SshCommand.SET_WIFI_R0_MASK + "=" + WireLess_ConfigR0New[index][1].getText();
                Qry += mask0 + ";";
            }
            if (!WireLess_ConfigR1New[index][1].getText().equals("") ) {
                String mask1 = SshCommand.SET_WIFI_R1_MASK + "=" + WireLess_ConfigR1New[index][1].getText();
                Qry += mask1 + ";";
            }
            
            if (!WireLess_ConfigR0New[index][2].getText().equals("")) {
                String channel0 = SshCommand.SET_WIFI_R0_CHANNEL + "=" + WireLess_ConfigR0New[index][2].getText();
                Qry += channel0 + ";";

            }

            if (!WireLess_ConfigR1New[index][2].getText().equals("")) {
                String channel1 = SshCommand.SET_WIFI_R1_CHANNEL + "=" + WireLess_ConfigR1New[index][2].getText();
                Qry += channel1 + ";";
            }
          //  String Qry = Radio0+";"+Radio1+";"+channel0+";"+channel1;
            global.InterfaceFactory.getSessioninterface().generateExecCommands(IP,Qry);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
   } 
