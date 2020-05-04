package network;

public class NetworkBasicClient {

    private String address;
    private int port;
    private ThreadClient thread;
    
    public NetworkBasicClient(String address, int port) {
        setAddress(address);
        setPort(port);
        this.thread = new ThreadClient(address, port);
        this.thread.start();
    }
    
    public String sendString(String s) {
        return this.thread.sendString(s);
    }
    
    public void sendStringWithoutWaiting(String s) {
        this.thread.sendStringWithoutWaiting(s);
    }
    
    public void setEndSending() {
        this.thread.sendStringWithoutWaiting("**EOC**");
        System.out.println("Client d: ");
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public final void setAddress(String address) {
        this.address = address;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(int port) {
        this.port = port;
    }

}
