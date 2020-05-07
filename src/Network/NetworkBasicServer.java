package Network;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Florent
 */
public class NetworkBasicServer extends Network implements IInOutEvent {

    // Properties
    
    public JLabel LOnOff = null;
    public JButton BtnDemarrerServeur = null;
    
    // Constructors
    
    public NetworkBasicServer(int port, IInOutEvent inOutEvent, JLabel LOnOff, JButton BtnDemarrerServeur) throws Exception {
        super(port, inOutEvent);
        this.LOnOff = LOnOff;
        this.BtnDemarrerServeur = BtnDemarrerServeur;
    }
    
    // Methods
    
    public void connect() {
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
    
    // Getters and setters

    public NetworkThread getThread() {
        return thread;
    }
    
}
