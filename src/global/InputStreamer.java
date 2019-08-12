/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/*
*
* @(#) InputStreamer.java
*
*/

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;

import javax.swing.JTextField;

public class InputStreamer extends InputStream implements KeyListener {

    private JTextField tf;
    private String str = null;
    private int pos = 0;

    public InputStreamer(JTextField jtf) {
        tf = jtf;
    }

    //gets triggered everytime that "Enter" is pressed on the textfield
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER)
        {
        str = tf.getText() + "\n";
        pos = 0;
        //tf.setText("");
        synchronized (this) {
             this.notifyAll();
        }
        }
    }

    @Override
    public int read() {
        //test if the available input has reached its end
        //and the EOS should be returned 
        if(str != null && pos == str.length()){
            
            System.out.println(str);
            str =null;
            return java.io.StreamTokenizer.TT_EOF;
        }
        while (str == null || pos >= str.length()) {
            try {

                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        tf.setText("");
        //read an additional character, return it and increment the index
        return str.charAt(pos++);
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
