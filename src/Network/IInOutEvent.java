package Network;

/**
 *
 * @author Florent
 */
public interface IInOutEvent {
    
    String getMessage();
    String readMessage();
    void sendMessage(String message);
    void messageReceived(); // Invoked when a message is received in thread
    
}
