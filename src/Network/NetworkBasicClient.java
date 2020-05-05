package Network;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Florent
 */
public class NetworkBasicClient {

    // Properties
    
    private String serverName;
    private int port = -1;
    private JLabel LOnOff = null;
    private JButton BtnSeConnecterAuServeur = null;
    private ThreadClient thread = null;
    
    // Constructors
    
    public NetworkBasicClient(String serverName, int port, JLabel LOnOff, JButton BtnSeConnecterAuServeur) throws Exception {
        this.setServerName(serverName);
        this.setPort(port);
        this.LOnOff = LOnOff;
        this.BtnSeConnecterAuServeur = BtnSeConnecterAuServeur;
    }
    
    // Functions
    
    public void connect() throws Exception {
        if (this.isDisconnected())
            this.thread = new ThreadClient(this.serverName, this.port, this.LOnOff, this.BtnSeConnecterAuServeur);
        this.thread.start();
    }
    
    public boolean isConnected() {
        if (this.thread == null)
            return false;
        return this.thread.getInService();
    }
    
    public void disconnect() {
        if (this.thread != null) {
            this.thread.close(true);
            this.thread = null;
        }
    }
    
    public boolean isDisconnected() {
        if (this.thread == null)
            return true;
        return !this.thread.getInService();
    }
    
    public void sendMessage(String message) {
        this.thread.sendMessage(message);
    }
    
    // Getters and setters
    
    public String getServerName() {
        return this.serverName;
    }
    
    public final void setServerName(String serverName) throws Exception {
        if (serverName.isEmpty())
            throw new Exception("[NetworkBasicClient | Error] Server name can't be empty.");
        this.serverName = serverName;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(int port) throws Exception {
        if (port < 0)
            throw new Exception("[NetworkBasicClient | Error] Port must be a positive integer.");
        this.port = port;
    }

}
