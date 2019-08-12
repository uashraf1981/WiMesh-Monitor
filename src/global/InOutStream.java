/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Abdullah
 */
public class InOutStream {
   public static javax.swing.JTextArea [] consoles = new javax.swing.JTextArea [CONSTANTS.ROUTERS];
    public static PrintStream[] out = new PrintStream[CONSTANTS.ROUTERS];
    public static InputStream[] in = new InputStream[CONSTANTS.ROUTERS];
    public static void setConsoleGUIs(javax.swing.JTextArea [] c, PrintStream [] o )
    {
        consoles = c;
        out = o;
    }
}
