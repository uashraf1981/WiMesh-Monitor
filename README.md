# WiMesh-Monitor
This software is being released under the terms of the GNU General Public License.

Basics
------
The WiMesh-Monitor open source tool was designed to be able to serve as a basic security monitoring tool for networks based on embedded devices running OpenWRT Linux. It spans over 3000 lines of code in Java and Shell Scripts for Linux. It performs the following functions:

1. Communication - Allows a central server to open parallel TCP connection to all end points through specific ports
2. Data Collection - Extensive collection, aggregation and logging of end point data into a central repository
3. Breach Containment - Remotely terminating malicious processes, or the complete end point device

WiMesh-Monitor allows for a central server to remotely open ports to the network devices and subsequently collect extensive amounts of data from the end points including traffic, queue lengths, kernel logs, running processes, memory state and several other metrics. The central server acts as a central repository where all of this data is collected and can later be analyzed. It is a user-friendly and plug-and-play kind of a tool where you just need to update the XML file which is loaded at boot time to map to your own network. The extensive data collection can help in monitoring network performance, detecting anomalies etc. Another added advantage is that we can remotely monitor and terminate suspicious or malicious processes or contain a breach by instantly turning off specific end points remotely.

The WiMesh-Monitor tool was initially used on a 22-node indoor network made up of hacked TP-Link WDR4300 dual-band MIMO routers by replacing its firmware with OpenWRT embedded Linux to allow for fine-tuned control. 

The project has around 30 source files and 3000 lines of code (GUI and resources excluded).

The flow of the tool is shown in the figure below:
![stack Overflow](https://github.com/uashraf1981/WiMesh-Controller/blob/master/WiMesh%20Controller%20Flow.png)

Functionality:
--------------
- Listing the status of all end points (up, down, restarting)
- Log data collection from end points including kernel logs, processes in memory, traffic statistics, free memory, IP, files
- Snapshot of the entire network i.e. all end points including their OS, configuration files, memory map
- Network restoration to a previous state
- Single window remote shell access to all end points
- Remotely edit files on all end points
- Remotely enable or disable network interfaces, terminate suspicious processes, or shut down the end point 

Some snapshots of the interface of the tool are shown below:
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/WiMesh_Controller_a.jpg)
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/WiMesh_Controller_b.jpg)
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/Logging.jpg)
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/processinfo.jpg)
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/remoteshell.jpg)

Description of the core functionality of main files in WiMesh Monitor
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
This class handles the backup functionality of the software. It allows the control station to take the live snapshot of all the files, configurations and the firmware (.bin ) flashed onto the router. We can specify a single router or select all the routers at once. The snapshot images are compressed and saved along with the date time stamps for reference. The same images can later be used through the software to setup a particular image onto the router. The select all option is like a snapshot of the entire network and our tool later allows to restore the whole network.

Logging.java
------------
This class handles the pre-amble for logging.

ProcessInfo.java
----------------
This class collects the details of all the processes running on the target router and displays it. The class also allows for remotely killing a particular process running on the router through the PID.

RouterInfo.java
----------------
This class handles all sort of data colletion from the router. It collecs information about the network configuration, including the wireless and ethernet interfaces, as well as other key router statistics including interface IPs, router uptimne, traffic statistics, node status, along with several different kinds of logging worthy data. 

Classes Defining the Data Structures
------------------------------------
RadiosInfo.java
---------------
{ RouterIpAddress, Ssid, Mode, Channel, Traffic, Status, RadioIpAddress, Mask, Mtu, RxPackets, RxBytes, TxPackets, TxBytes,   
  TxQLength, RouterIpAddress, Channel }
  
RouterNode.java
---------------
{ ID, IP, Mask, IsAlive }
