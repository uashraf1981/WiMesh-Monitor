/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

import javax.swing.JComboBox;

/**
 *
 * @author ICTWimesh
 */
public class ComboBoxes {
    
    public static void clearComboBoxes(JComboBox...jcb){
        
        for(JComboBox j : jcb)
        {
            j.removeAllItems();
        }
        /*
                BootRest_RouterList.removeAllItems();
                Command_RouterList.removeAllItems();
                Upload_RouterList.removeAllItems();
                BackUp_RouterList.removeAllItems();
                
        */
    }
    public static void addAllToComboBoxes(JComboBox...jcb){
        
        for(JComboBox j : jcb)
        {
            j.insertItemAt("All", 0);
        }
        /*
               BootRest_RouterList.insertItemAt("All", 0);
                Upload_RouterList.insertItemAt("All", 0);
                BackUp_RouterList.insertItemAt("All", 0);
                
        */
    }
    public static void setindexesToTop(JComboBox...jcb){
        
        for(JComboBox j : jcb)
        {
            j.setSelectedIndex(1);
        }
        /*
                BootRest_RouterList.setSelectedIndex(1);
                Upload_RouterList.setSelectedIndex(1);
                BackUp_RouterList.setSelectedIndex(1);
                Command_RouterList.setSelectedIndex(1);
        */
    }
    
}
