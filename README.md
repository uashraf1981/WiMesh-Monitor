# WiMesh-Monitor
# This software is being released under the terms of the GNU General Public License.

A custom designed remote configuration, logging and monitoring tool for Wireless Networks

![stack Overflow](https://github.com/uashraf1981/WiMesh-Controller/blob/master/WiMesh%20Controller%20Flow.png)

Functionality:
--------------
- Kernel Logs
- Process Info
- Traffic Statistics (RxPacket, TxPackets)
- Node Up Time
- Connected Users
- Routing Table
- IP
- Reboot
- Kill Process
- Free Memory
- Date
- Wireless Configuration
- Ifconfig
- Wireless Radio Channel
- Wireless Radio Mask
- Take snapshot backup of a selected or all routers (OS, configuration files etc.)


Description of the core functionalit of main files in WiMesh Monitor
--------------------------------------------------------------------
Boot.java
---------
1. Parse the MasterList.xml and note the total number of nodes in the network, their IDs and their IP Addresses
2. Open SSH connections to all the routers mentioned in the MasterList.xml file. If some of them are not responding then show  
   their identities 
3. Check that the Router ID, IP and Mask in the WiMesh.info match those in the MasterList.xml if not then display those 
   routers/entries
4. For each router, update the /etc/config/network and /etc/config/wireless files which contain the interface (Wireless or 
   Wired NICs) according to the information contained in the WiMesh.info file
5. Reboot the routers
6. Re-open SSH connections to the Routers. If some are not responding show their identities
7. Re-check that the Router ID, IP and Mask currently assigned to the router matches the info in their WiMesh.info file. 
8. Check if Router RAM/ROM memory nearly full (90%), issue warning for the nodes that have
9. Show System OK message!

XMLParser.java
--------------
Parses the MasterList.xml file and retrieves the router ID, IP Addresses, Masks and other relevant details.

MeshCommunicationUnit.java
--------------------------
This important class makes extensive use of the JSch (Java Shell) API to create shell sessions for each router that was specified in the xml file. It connects on ports 22 of the routers (rem. that SSH has been enabled and the daemon is running beause of the boot file that we used earlier). For the shell sessions, the appropriate usernames and passwrds are incporporated automatically. The class also handles graceful termination of the shell sessions.

File.java
---------
This class handles all aspects of remote file upload/download to/from the control station and the routers using the secure SFTP protocol and contains the appropriate code for it. Separate sessions (sockets) are generated for the file transfer which are later gracefully terminated once the file transfer is complete. The class provides a nice GUI file locater.

Backup.java
---------
This class handles all aspects of remote file upload/download to/from the control station and the routers using the secure SFTP protocol and contains the appropriate code for it. Separate sessions (sockets) are generated for the file transfer which are later gracefully terminated once the file transfer is complete. The class provides a nice GUI file locater.

Classes Defining the Data Structures
------------------------------------
RadiosInfo.java
---------------
{ RouterIpAddress, Ssid, Mode, Channel, Traffic, Status, RadioIpAddress, Mask, Mtu, RxPackets, RxBytes, TxPackets, TxBytes,   
  TxQLength, RouterIpAddress, Channel }
  
RouterNode.java
---------------
{ ID, IP, Mask, IsAlive }
