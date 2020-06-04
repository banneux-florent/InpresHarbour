package Network;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Florent
 */
public class NetworkBasicClient extends Network implements IInOutEvent {

    // Properties
    
    private String host;
    public JLabel LOnOff = null;
    public JButton BtnSeConnecterAuServeur = null;
    
    // Constructors
    
    public NetworkBasicClient(String serverName, int port, IInOutEvent inOutEvent, JLabel LOnOff, JButton BtnSeConnecterAuServeur) throws Exception {
        super(port, inOutEvent);
        this.setHost(serverName);
        this.LOnOff = LOnOff;
        this.BtnSeConnecterAuServeur = BtnSeConnecterAuServeur;
    }
    
    // Network
    
    @Override
    public void connect() {
        if (this.isDisconnected()) {
            this.thread = new ThreadClient(this);
            this.thread.start();   
        }
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
    
    public String getHost() {
        return this.host;
    }
    
    public final void setHost(String host) throws Exception {
        if (host.isEmpty())
            throw new Exception("[NetworkBasicClient | Error] Host can't be empty.");
        this.host = host;
    }
    
    public NetworkThread getThread() {
        return this.thread;
    }

}
