/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.util.LinkedList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Florent
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Frame {
    
    // Properties
    
    @XmlElement
    private LinkedList<String> args = new LinkedList<String>();
    
    // Constructors
    
    public Frame() {}
    
    // Methods

    public LinkedList<String> getArgs() {
        return args;
    }

    public void setArgs(LinkedList<String> args) {
        this.args = args;
    }
    
    public String getArg(int iArg) {
        if (iArg >= 0)
            return this.args.get(iArg);
        return "";
    }
    
    public void addArg(String arg) {
        this.args.add(arg);
    }
    
    public void removeArg(int iArg) {
        this.args.remove(iArg);
    }
    
    public String getAction() {
        if (this.args.size() > 0)
            return this.getArg(0);
        return "";
    }
    
    @XmlTransient
    public void setAction(String action) {
        if (this.args.isEmpty())
            this.addArg(action);
        else
            this.args.set(0, action);
    }
    
    public static void send(Network network, String[] args) {
        if (network.isConnected()) {
            Frame frame = new Frame();
            for (String arg : args) {
                frame.addArg(arg);
            }
            try {
                network.sendMessage(XMLFormatter.toXML(frame));
            } catch (Exception e) {
                System.err.println("[Frame | Error] \"" + e.getMessage() + "\"");
            }
        }
    }
    
    
}
