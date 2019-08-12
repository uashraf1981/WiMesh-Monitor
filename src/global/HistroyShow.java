/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

import gui.WiMeshMainForm;
import static gui.WiMeshMainForm.startHistroy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ICTWimesh
 */
public class HistroyShow {
    
   private Thread t;
   private String threadName;
    public HistroyShow(String name){
       threadName = name;
       System.out.println("Creating " +  threadName );
   }
 
   public void run() throws InterruptedException, FileNotFoundException, IOException 
   {
            File file = new File("D://MyFile.txt");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            //
            String s = new String(data, "UTF-8");
            String[] HistroyArr = s.split("\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*");
            for (int i = 0; i < HistroyArr.length; i++) {
                String[] Routerinfo = HistroyArr[i].split("\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.\\.");
                for (int j = 0; j < Routerinfo.length; j++) {
                    startHistroy(Routerinfo[j],j);
                }
                Thread.sleep(1000);
            }
   }
   
   public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread ();
         t.start ();
      }
   }

}

//public class TestThread {
//   public static void main(String args[]) {
//   
//      
//      
//      ThreadDemo T2 = new ThreadDemo( "Thread-2");
//      T2.start();
//   }   
//}

