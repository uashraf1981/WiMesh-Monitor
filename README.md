# WiMesh-Monitor
This software is being released under the terms of the GNU General Public License.

Basics
------
Due to my significant involvement in wireless and IoT related experimental work on real network testbeds, I needed a tool which could allow for the following:

  - A monitoring tool with a user-friendly GUI indicating the status and statistics of all nodes in a wireless network
  - A tool which allowed for remotely controlling, configuring, rebooting the nodes
  - Extensive logging of information at nodes (traffic, channel, queue, kernel logs, running processes) 

For this particular project, we designed and deployed a 22-node indoor network made up of hacked TP-Link WDR4300 dual-band MIMO routers by replacing its firmware with OpenWRT embedded Linux to allow for fine-tuned control. Moreover, I had designed a new failure-resistant routing metric which could provide connectivity even if an attack or natural disaster brought down some nodes. The resilience and performance of the proposed ELP metric was far superior to the existing state-of-the-art metrics. The metric was impleneted in the AODV protocol on OpenWRT for these routers. The biggest problem in managing this 22-node network was to remotely control and log the traffic information passing through the network. I took it upon me to design this tool which could provide all these functionality. I ended up putting a lot more functionality than was initially envisioned - basically a comprehensive tool which could make life easy for researchers looking to remotely configure and control their testbeds. 

The flow of the tool is shown in the figure below:
![stack Overflow](https://github.com/uashraf1981/WiMesh-Controller/blob/master/WiMesh%20Controller%20Flow.png)

Functionality:
--------------
- Listing the current nodes status (up, down, restarting)
- Live statistics of routes, network interfaces, channels, traffic, node uptime, received packets, transmitted packets
- Information on all the running processes on a node
- Logging and the ability to view those logs including kernel logs, free memory router ID, router IP, received packets, 
  transmitted packets, lost packets, interface ID, interface IP, and transmission queue length
- Files and folders upload and download
- Backup of the current state of all the network routers
- Network restore to a previous state
- Single screen remote shell access for fine-tuned control
- Remote editing of the files on any router
- Remote control od nodes including reboot, modification of the radio interfaces of any router
- Take snapshot backup of a selected or all routers (OS, configuration files etc.)

Some snapshots of the interface of the tool are shown below:
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/WiMesh_Controller_a.jpg)
![stack Overflow](https://github.com/uashraf1981/WiMesh-Monitor/blob/master/src/res/WiMesh_Controller_b.jpg)

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
