/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author ICTWimesh
 */
public class EthObject {

    private String Mode;

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getTraffic() {
        return Traffic;
    }

    public void setTraffic(String Traffic) {
        this.Traffic = Traffic;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMask() {
        return Mask;
    }

    public void setMask(String Mask) {
        this.Mask = Mask;
    }

    public String getMtu() {
        return Mtu;
    }

    public void setMtu(String Mtu) {
        this.Mtu = Mtu;
    }

    public String getRxPackets() {
        return RxPackets;
    }

    public void setRxPackets(String RxPackets) {
        this.RxPackets = RxPackets;
    }

    public String getRxBytes() {
        return RxBytes;
    }

    public void setRxBytes(String RxBytes) {
        this.RxBytes = RxBytes;
    }

    public String getTxPackets() {
        return TxPackets;
    }

    public void setTxPackets(String TxPackets) {
        this.TxPackets = TxPackets;
    }

    public String getTxBytes() {
        return TxBytes;
    }

    public void setTxBytes(String TxBytes) {
        this.TxBytes = TxBytes;
    }

    public String getTxQLength() {
        return TxQLength;
    }

    public void setTxQLength(String TxQLength) {
        this.TxQLength = TxQLength;
    }

    public String getETHIP() {
        return ETHIP;
    }

    public void setETHIP(String ETHIP) {
        this.ETHIP = ETHIP;
    }
    private String Traffic;
    private String Status; 
    private String Mask;
    private String Mtu;
    private String RxPackets;
    private String RxBytes;
    private String TxPackets;
    private String TxBytes;
    private String TxQLength;
    private String ETHIP;
    
    public static Hashtable<String,EthObject> ETHList = new Hashtable<String,EthObject>();
 
}
