package Network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

// To get trace of exception error
// Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, (String)null, exception);

/**
 *
 * @author Florent
 */
public class ThreadClient extends Thread {
    
    // Properties

    private NetworkBasicClient client;
    
    private Socket clientSocket = null;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    private LinkedList messagesList = new LinkedList();
    private boolean inService = false;
    
    // Constructors
    
    public ThreadClient(NetworkBasicClient client) throws Exception {
        this.client = client;
    }
    
    // Methods
    
    public void run() {
        System.out.println("[ThreadClient | Info] Connecting client...");
        this.inService = true;
        
        // Client's socket creation
        try {
            this.clientSocket = new Socket(this.client.getServerName(), this.client.getPort());
        } catch (UnknownHostException e) {
            System.err.println("[ThreadClient | Error] Host unknown.");
            if (this.inService) this.close(true);
            return;
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] \"" + e + "\". Connection missing ?");
            if (this.inService) this.close(true);
            return;
        }
        if (this.clientSocket == null) {
            System.err.println("[ThreadClient | Error] Client socket couln't be created.");
            if (this.inService) this.close(true);
            return;
        } else {
            System.out.println("[ThreadClient | Info] Client connected: " + this.clientSocket.getInetAddress().toString());
            this.client.LOnOff.setText("ON");
            this.client.LOnOff.setForeground(Color.GREEN);
            this.client.BtnSeConnecterAuServeur.setText("Se déconnecter du serveur");
        }
        
        // Creating read/write buffers
        try {
            this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] \"" + e + "\". Connection missing ?");
            if (this.inService) this.close(true);
            return;
        }
        if (this.bufferRead == null || this.bufferWrite == null) {
            System.err.println("[ThreadClient | Error] Read or write buffer couldn't be created.");
            if (this.inService) this.close(true);
            return;
        } else {
            System.out.println("[ThreadClient | Info] Stream created.");
        }
        
        // Client running loop
        String message = null;
        do {
            try {
                System.out.println("[ThreadClient | Info] Waiting for an input...");
                message = this.bufferRead.readLine();
                System.out.println("[ThreadClient | Info] [<<<] " + message);
                if (message.equals("server_stopping")) { // Server is closing, client needs to stop
                    this.close(false);
                } else if (message.equals("client_stop_ok")) { // Server confirmation for stopping client
                    System.out.println("[ThreadClient | Info] Server sended confirmation to stop.");
                } else {
                    if (!addMessage(message)) {
                        System.err.println("[ThreadClient | Error] Couldn't save the input.");
                    }
                }
            } catch (Exception e) {
                System.err.println("[ThreadClient | Error] \"" + e + "\".");
            }
        } while (this.inService);
        System.out.println("[ThreadClient | Info] Client stopped.");
    }
    
    public void close(boolean confirmation) {
        this.inService = false;
        this.client.LOnOff.setText("OFF");
        this.client.LOnOff.setForeground(Color.RED);
        this.client.BtnSeConnecterAuServeur.setText("Se connecter au serveur");
        try {
            if (confirmation)
                this.sendMessage("client_stop_confirmation"); // Send closing alert to the server with the need of a confirmation (to unlock bufferRead.readLine())
            else
                this.sendMessage("client_stop_noconfirmation"); // Send closing alert to the server without needing confirmation
            if (this.bufferRead != null)
                this.bufferRead.close();
            if (this.bufferWrite != null)
                this.bufferWrite.close();
            if (this.clientSocket != null)
                this.clientSocket.close();
        } catch (Exception e) {
            System.err.println("[ThreadClient | Error] " + e);
        }
        System.out.println("[ThreadClient | Info] Stopping client...");
    }
    
    public void sendMessage(String message) {
        if (this.bufferWrite != null) {
            try {
                this.bufferWrite.write(message);
                this.bufferWrite.newLine();
                this.bufferWrite.flush();
                System.out.println("[ThreadClient | Info] [>>>] " + message);
            } catch (IOException ex) {
                Logger.getLogger(NetworkBasicClient.class.getName()).log(Level.SEVERE, (String)null, ex);
            }
        }
    }

    public synchronized boolean addMessage(String str) {
        if (this.messagesList.add(str)) {
            this.client.messageReceived();
            return true;
        }
        return false;
    }

    public synchronized String getMessage() {
        if (!this.messagesList.isEmpty()) {
            String str = (String) this.messagesList.getFirst();
            this.messagesList.removeFirst();            
            return str;
        }
        return "";
    }

    public synchronized String readMessage() {
        return (!this.messagesList.isEmpty()) ? (String) this.messagesList.getFirst() : "";
    }

    public synchronized String[] getAllMessages() {
        return (String[])this.messagesList.toArray();
    }
    
    /*
    public String receiveString() {
        String response = null;
        try {
            response = this.bufferRead.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
        return response;
    }
    */
    
    // Getters and setters
    
    public boolean getInService() {
        return this.inService;
    }

    public BufferedReader getBufferRead() {
        return bufferRead;
    }

    public BufferedWriter getBufferWrite() {
        return bufferWrite;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
    
}
