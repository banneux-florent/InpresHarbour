package Network;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Florent
 */
public class NetworkBasicServer implements IInOutEvent {

    // Properties
    
    private IInOutEvent inOutEvent;
    
    private int port = -1;
    public JLabel LOnOff = null;
    public JButton BtnDemarrerServeur = null;
    private ThreadServer thread = null;
    
    // Constructors
    
    public NetworkBasicServer(int port, IInOutEvent inOutEvent, JLabel LOnOff, JButton BtnDemarrerServeur) throws Exception {
        this.setPort(port);
        this.LOnOff = LOnOff;
        this.BtnDemarrerServeur = BtnDemarrerServeur;
        this.inOutEvent = inOutEvent;
    }
    
    // Methods
    
    public void connect() throws Exception {
        if (this.isDisconnected()) {
            this.thread = new ThreadServer(this);
        }
        this.thread.start();
    }
    
    public boolean isConnected() {
        if (this.thread == null)
            return false;
        return this.thread.getInService();
    }
    
    public void disconnect() {
        if (this.thread != null) {
            this.thread.close();
            this.thread = null;
        }
    }
    
    public boolean isDisconnected() {
        if (this.thread == null)
            return true;
        return !this.thread.getInService();
    }
    
    @Override
    public String getMessage() {
        return this.thread.getMessage();
    }
    
    @Override
    public String readMessage() {
        return this.thread.readMessage();
    }
    
    @Override
    public void sendMessage(String message) {
        this.thread.sendMessage(message);
    }
    
    @Override
    public void messageReceived() {
        // Invoked when a message is received in thread
        System.out.println("[NetworkBasicServer | Info] [<<<] " + this.readMessage());
        this.inOutEvent.messageReceived();
    }
    
    // Getters and setters
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(int port) throws Exception {
        if (port < 0)
            throw new Exception("[NetworkBasicServer | Error] Port must be a positive integer.");
        this.port = port;
    }

    public ThreadServer getThread() {
        return thread;
    }
    
}
