/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

/**
 *
 * @author Florent
 */
public interface NetworkThread {
    
    void start();
    void close();
    void sendMessage(String message);
    boolean receiveMessage(String str);
    String getMessage();
    String readMessage();
    boolean getInService();
    
}
