/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

/**
 *
 * @author flore
 */
public interface NetworkThread {
    
    void start();
    void close();
    void sendMessage(String message);
    boolean addMessage(String str);
    String getMessage();
    String readMessage();
    String[] getAllMessages();
    boolean getInService();
    
}
