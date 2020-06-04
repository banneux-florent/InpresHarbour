package Network;

/**
 *
 * @author Florent
 */
public abstract class Network implements IInOutEvent {
    
    // Properties
    
    private final IInOutEvent inOutEvent;
    
    private int port = -1;
    protected NetworkThread thread = null;
    
    // Constructors
    
    public Network(int port, IInOutEvent inOutEvent) throws Exception {
        this.setPort(port);
        this.inOutEvent = inOutEvent;
    }
    
    // Methods
    
    public abstract void connect();
    
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
    
    // IInOutEvent
    
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
            throw new Exception("[Network | Error] Port must be a positive integer.");
        this.port = port;
    }
    
}
