/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

public class RouterNode
{
    public String ID;
    public String IP;
    public String Mask;
    public boolean isAlive;

    public RouterNode(){}
    public RouterNode(String id, String ip, String mask)
    {
       ID = id;
       IP = ip;
       Mask=mask;
    }
    public void setID(String id) {ID=id;}    
    public void setIP(String ip) {IP=ip;}   
    public void setisAlive(boolean isalive) {isAlive=isalive;}    
    public void setMask(String mask) {Mask=mask;}  
    public String getID() {return ID;}    
    public String getIP() {return IP;}   
    public boolean getisAlive() {return isAlive;}    
    public String getMask() {return Mask;}  
}

