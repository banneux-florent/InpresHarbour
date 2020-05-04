package network;

import javax.swing.JCheckBox;

public class NetworkBasicServer {

    private int port;
    private JCheckBox checkBoxCalled;
    private ThreadServeur thread;
    
    public NetworkBasicServer(int port, JCheckBox checkbox) {
        setPort(port);
        this.checkBoxCalled = checkbox;
        this.thread = new ThreadServeur(this.port, this.checkBoxCalled);
        this.thread.start();
    }
    
    public void setEndReceiving() {
        this.thread.stopThread();
    }
    
    public String getMessage() {
        return this.thread.getMessage();
    }
    
    public void sendMessage(String message) {
        System.out.println("« " + message + " » envoyé au serveur (port " + port + ")");
        this.thread.sendMessage(message);
    }
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(int port) {
        this.port = port;
    }

}
