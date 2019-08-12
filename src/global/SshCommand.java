/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

/**
 *
 * @author ICTWimesh
 */
public class SshCommand {
    public static final String NODE_UP_TIME = "uptime"; // give us uptime, No of connected users, load average i,e07:16:09 up 12 days, 21:43,  3 users,  load average: 0.00, 0.01, 0.05
   
    public static final String CONNECTED_USERS = "who"; // give us full detail of user List 
    /*
    USER     TTY      FROM             LOGIN@   IDLE   JCPU   PCPU WHAT
    aftab    pts/0    115-186-137-9.na 06:48   27:41   0.00s  0.00s -sh
    aftab    pts/1    115-186-137-9.na 06:48   27:39   0.00s  0.00s -sh
    aftab    pts/3    115-186-137-9.na 06:55    0.00s  0.00s  0.00s w

    */
    public static final String ROUTING_TABLE = "route";//netstat -rn"; // give us Routing Table Info  // route does
    /*
    Kernel IP routing table
    Destination     Gateway         Genmask         Flags Metric Ref    Use Iface
    default         162.243.254.1   0.0.0.0         UG    0      0        0 eth0
    162.243.254.0   *               255.255.255.0   U     0      0        0 eth0
    */
   public static final String IP = "route"; // give us Router IP Address

   public static final String REBOOT = "reboot"; // REBOOT Router
   
   public static final String PROCESSING_INFO = "ps"; // give us Router IP Address
   
   public static final String KILL_PROCESS = "kill"; // kill <pid>
   //public static final String KILL_PROCESSES = "kill {0}"; // kill pid
   
   public static final String FREE_MEMORY = "free"; // show total memory, used memory, and free memory. -m will show it in megabytes instead of kilobytes
      
   //For Logging Below Commands will be used
   
   public static final String DATE = "date";
   public static final String ECHO = ";echo end;";
   /*
   uptime;echo end;route;echo end;ps;echo end;free;echo end;
   uci get wireless.@wifi-iface[1].mode;echo ,;uci get wireless.@wifi-iface[1].ssid;
   echo ,;uci get wireless.radio0.channel;echo /; uci get wireless.@wifi-iface[0].mode;
   echo ,;uci get wireless.@wifi-iface[0].ssid;uci get wireless.radio1.channel;echo end;;ifconfig;echo end;; 
// gets Radio Mode, SSID, and channel
   */
   public static final String WIRELESS_CONF  = "uci get wireless.@wifi-iface[1].mode;echo ,;uci get wireless.@wifi-iface[1].ssid;echo ,;uci get wireless.radio0.channel;echo ,; uci get wireless.@wifi-iface[0].mode;echo ,;uci get wireless.@wifi-iface[0].ssid;echo ,;uci get wireless.radio1.channel";
   //String Wireless_conf =  "uci get wireless.@wifi-iface[1].mode;echo ,;uci get wireless.@wifi-iface[1].ssid;echo /; uci get wireless.@wifi-iface[0].mode;echo ,;uci get wireless.@wifi-iface[0].ssid ";
   public static final String KERNALLOGGS = "dmesg"; // get kernal logs of router
   public static final String GOBACK = "cd ../"; // get kernal logs of router
   public static final String IFCONFIG = "ifconfig"; // get kernal logs of router
   
   public static final String SET_WIFI_R0_IP = "uci set network.wireless.ipaddr";
   public static final String SET_WIFI_R1_IP = "uci set network.wireless2.ipaddr";
   
   public static final String GET_WIFI_R0_IP = "uci get network.wireless.ipaddr";
   public static final String GET_WIFI_R1_IP = "uci get network.wireless2.ipaddr";
   
   public static final String SET_WIFI_R0_CHANNEL = "uci set wireless.radio0.channel";
   public static final String SET_WIFI_R1_CHANNEL = "uci set wireless.radio1.channel";
   
   public static final String GET_WIFI_R0_CHANNEL = "uci get wireless.radio0.channel";
   public static final String GET_WIFI_R1_CHANNEL = "uci get wireless.radio1.channel";
   
    public static final String SET_WIFI_R0_MASK = "uci set network.wireless.netmask";
    public static final String SET_WIFI_R1_MASK = "uci set network.wireless.netmask";
   
}
