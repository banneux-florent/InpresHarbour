package Network;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Florent
 */
public class NetworkBasicClient extends Network implements IInOutEvent {

    // Properties
    
    private String serverName;
    public JLabel LOnOff = null;
    public JButton BtnSeConnecterAuServeur = null;
    
    // Constructors
    
    public NetworkBasicClient(String serverName, int port, IInOutEvent inOutEvent, JLabel LOnOff, JButton BtnSeConnecterAuServeur) throws Exception {
        super(port, inOutEvent);
        this.setServerName(serverName);
        this.LOnOff = LOnOff;
        this.BtnSeConnecterAuServeur = BtnSeConnecterAuServeur;
    }
    
    // Network
    
    @Override
    public void connect() {
        if (this.isDisconnected())
            this.thread = new ThreadClient(this);
        this.thread.start();
    }
    
    @Override
    public boolean isConnected() {
        if (this.thread == null)
            return false;
        return this.thread.getInService();
    }
    
    @Override
    public void disconnect() {
        if (this.thread != null) {
            this.thread.close();
            this.thread = null;
        }
    }
    
    @Override
    public boolean isDisconnected() {
        if (this.thread == null)
            return true;
        return !this.thread.getInService();
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
    
    public NetworkThread getThread() {
        return this.thread;
    }

}
