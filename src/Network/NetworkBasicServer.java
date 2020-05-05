package Network;

import javax.swing.JButton;
import javax.swing.JLabel;

public class NetworkBasicServer {

    // Properties
    
    private int port = -1;
    private JLabel LOnOff = null;
    private JButton BtnDemarrerServeur = null;
    private ThreadServeur thread = null;
    
    // Constructors
    
    public NetworkBasicServer(int port, JLabel LOnOff, JButton BtnDemarrerServeur) throws Exception {
        this.setPort(port);
        this.LOnOff = LOnOff;
        this.BtnDemarrerServeur = BtnDemarrerServeur;
    }
    
    // Methods
    
    public void connect() throws Exception {
        if (this.isDisconnected())
            this.thread = new ThreadServeur(port, this.LOnOff, this.BtnDemarrerServeur);
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
    
    public String getMessage() {
        return this.thread.getMessage();
    }
    
    public void sendMessage(String message) {
        System.out.println("[NetworkBasicServer | Info] ? " + message + " ? sent to server on port " + port + ".");
        this.thread.sendMessage(message);
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

}
