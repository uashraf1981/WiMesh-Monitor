/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meshconfigurationunit;

import gui.WiMeshMainForm;
import static gui.WiMeshMainForm.out_;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import meshcommunicationunit.Channel;

/**
 *
 * @author ICTWimesh
 */
/*
This class is responsible to execute Shell commands from CommandsConsoles, for each router
*/
public class Command {
    public JTextArea[] consoles;
    public Command(JTextArea[] ta){
        consoles = ta;
    }  
    public Command(){
    }
    public void clearConsoles(){
        for(int i=0 ; i< consoles.length ; i++){
            consoles[i].setText("");
        }
    }
    public void SetConsoles(){
        
    }
    public void executeCommand(JTextArea jta, int RouterID,String finalText){
            // TODO add your handling code here:
            try {
                System.out.println("Final Text: " + finalText);
                Channel.channelShell[RouterID].setOutputStream(out_[RouterID]);
                global.CONSTANTS.pin[RouterID].write((finalText + "\n").getBytes());
            } catch (IOException ex) {
                Logger.getLogger(WiMeshMainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
