/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

import java.io.PipedOutputStream;

/**
 *
 * @author ICTWimesh
 */
public class CONSTANTS {
    public static final String USER_ID = "root";
    public static final String PASSWORD = "root";  
    public static final int PORT = 22;
    public static final int ROUTERS =14;
    // TABS updated to the number of tabs in Jpanel when application open. 
    public static int TABS = 2;
    // if session created successfully with Router, ALIVE_ROUTERS update
    public static int ALIVE_ROUTERS = 0;
    
    public static PipedOutputStream [] pin = new PipedOutputStream [ROUTERS];
    public static String XmlFilePath = "XmlFiles\\MasterList.xml";
    public static String ROUTER_RESTORE_DIRECTORY = "etc/config";
    public static String ROUTER_ROOT_DIRECTORY = "/";
    static long TIMEINTERVAL_LOGS = 5 * 60000;
    static long TIMEINTERVAL_FILES = 60000;
}
