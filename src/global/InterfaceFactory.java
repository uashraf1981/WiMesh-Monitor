/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;
 
import meshcommunicationunit.Session;
import meshconfigurationunit.Command;
import meshconfigurationunit.InterfaceConfiguration;
import meshcontroller.Backup;
import meshcontroller.Boot;
import meshcontroller.File;
import meshmonitoringunit.Logging;
import meshmonitoringunit.ProcessInfo;
import meshmonitoringunit.RouterInfo;


/**
 *
 * @author ICTWimesh
 */
public class InterfaceFactory {
   public static Boot BOOT;
   public static RouterInfo ROUTERINFO;
   public static ProcessInfo PROCESSINFO;
   public static Command commands;
   public static File files;
   public static Session session_;
   public static Logging logging;
   public static Parser parser;
   public static Backup backup;
   public static InterfaceConfiguration interfaceconfiguration;
   
    public static void init()
    {
        ROUTERINFO = new RouterInfo();
        BOOT = new Boot();
        PROCESSINFO = new ProcessInfo();
        commands = new Command();
        files = new File();
        session_ = new Session();
        logging = new Logging();
        parser = new Parser(); 
        backup = new Backup(); 
        interfaceconfiguration = new InterfaceConfiguration();
    }
    
    public static InterfaceConfiguration getinterfaceconfigurationinterface(){
        return interfaceconfiguration;  
    }
    
    public static Backup getBackupinterface(){
        return backup;  
    }
    
    public static Parser getParserinterface(){
        return parser;  
    }
    
    public static Logging getlogsinterface(){
        return logging;  
    }
    
    
    public static Session getSessioninterface(){
        return session_;  
    }
    
    public static File getFileinterface(){
        return files;  
    }
    
    public static Command getCommandinterface(){
        return commands;  
    }
    
    public static RouterInfo getRouterinterface(){
        if(ROUTERINFO!=null)
            return ROUTERINFO;
        return null;  
    }
    
    public static Boot getBootinterface(){
        if(BOOT!=null)
            return BOOT;
        return null;  
    }
    public static ProcessInfo getProcessinterface(){
        if(PROCESSINFO!=null)
            return PROCESSINFO;
        return null;  
    }
}
