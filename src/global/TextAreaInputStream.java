/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/*
*
* @(#) TextAreaOutputStream.java
*
*/

import java.io.InputStream;
import javax.swing.JTextArea;

public class TextAreaInputStream extends InputStream {
    private JTextArea textControl;

    /**
     * Creates a new instance of TextAreaOutputStream which writes
     * to the specified instance of javax.swing.JTextArea control.
     *
     * @param control   A reference to the javax.swing.JTextArea
     *                  control to which the output must be redirected
     *                  to.
     */
    public TextAreaInputStream( JTextArea control ) {
        textControl = control;
    }

/*
    public int read(byte[] b ) throws IOException {
        // append the data as characters to the JTextArea control
        //textControl.append( String.valueOf( ( char )b ) );
        String text = textControl.getText();
        return Byte.valueOf(text);
                
//                Integer.parseInt(String.valueOf( ( char )b ));
    }  
    * */
    
    public int read()
    {        
        String t = textControl.getText();
               //t = textControl.
        System.out.println("** Text is: "+t);
        return Integer.parseInt(textControl.getText());
    }
}
