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
public interface IInOutEvent {
    
    String getMessage();
    String readMessage();
    void sendMessage(String message);
    void messageReceived(); // Invoked when a message is received in thread
    
}
