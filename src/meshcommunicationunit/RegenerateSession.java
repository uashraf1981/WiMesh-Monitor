/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meshcommunicationunit;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import global.CONSTANTS;
import global.InOutStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static meshcommunicationunit.Session.session;
import meshmonitoringunit.RouterInfo;

/**
 *
 * @author ICTWimesh
 */

    /*
        When Ever WimeshController lost a connection with a router,
        This class will try to re-establish connection with the router. 
    */
public class RegenerateSession extends Thread {

    String IP;
    int index;

    public RegenerateSession(int i, String IP_) {
        index = i;
        IP = IP_;
    }

    public void run() {
        try {
            JSch jsch = new JSch();
            session[index] = jsch.getSession(CONSTANTS.USER_ID, IP, 22);
            session[index].setPassword(CONSTANTS.PASSWORD);
            Properties prop = new Properties();
            prop.setProperty("StrictHostKeyChecking", "no");
            session[index].setConfig(prop);
            session[index].connect();
            // number of channels is equal to the number of different tabs int the system //
            // Globals.CHANNELS.channelExec = new Channel[CONSTANTS.NO_OF_TABS];            
            //  for (int channelNumb = 0; channelNumb < 1; channelNumb++) {
            Channel.channelShell[index] = session[index].openChannel("shell");
            Channel.channelShell[index].setOutputStream(InOutStream.out[index]);

            InputStream in = new PipedInputStream();
            global.CONSTANTS.pin[index] = new PipedOutputStream((PipedInputStream) in);

            Channel.channelShell[index].setInputStream(in);
            Channel.channelShell[index].connect();
            Channel.channelShell[index].getSession().sendKeepAliveMsg();

            RouterInfo.routerList.get(index).isAlive = true;
            System.out.println(" Here ");

        } catch (JSchException e) {
            RouterInfo.routerList.get(index).isAlive = false;
            System.err.println(e);
        } catch (Exception ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
