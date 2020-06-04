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

/**
 *
 * @author Florent
 */
public class ThreadClient extends Thread implements NetworkThread {
    
    // Properties

    private final NetworkBasicClient client;
    
    private Socket clientSocket = null;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    private final LinkedList<String> messagesList = new LinkedList<>();
    private boolean inService = false;
    
    private boolean closeConfirmation = true;
    
    // Constructors
    
    public ThreadClient(NetworkBasicClient client) {
        this.client = client;
    }
    
    // Methods
    
    @Override
    public void run() {
        System.out.println("[ThreadClient | Info] Connecting client...");
        this.inService = true;
        
        // Client's socket creation
        try {
            this.clientSocket = new Socket(this.client.getHost(), this.client.getPort());
        } catch (UnknownHostException e) {
            System.err.println("[ThreadClient | Error] Host unknown.");
            if (this.inService) this.close();
            return;
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] \"" + e + "\". Connection missing ?");
            if (this.inService) this.close();
            return;
        }
        if (this.clientSocket == null) {
            System.err.println("[ThreadClient | Error] Client socket couln't be created.");
            if (this.inService) this.close();
            return;
        } else {
            System.out.println("[ThreadClient | Info] Client connected: " + this.clientSocket.getInetAddress().toString());
            this.client.LOnOff.setText("ON");
            this.client.LOnOff.setForeground(Color.GREEN);
            this.client.BtnSeConnecterAuServeur.setText("Se déconnecter du serveur");
        }
        
        // Getting socket's read/write buffers
        try {
            this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] \"" + e + "\". Connection missing ?");
            if (this.inService) this.close();
            return;
        }
        if (this.bufferRead == null || this.bufferWrite == null) {
            System.err.println("[ThreadClient | Error] Read or write buffer couldn't be created.");
            if (this.inService) this.close();
            return;
        } else {
            System.out.println("[ThreadClient | Info] Stream retrived.");
        }
        
        // Client running loop
        String message;
        while (this.inService) {
            try {
                System.out.println("[ThreadClient | Info] Waiting for an input...");
                message = this.bufferRead.readLine();
                System.out.println("[ThreadClient | Info] [<<<] " + message);
                if (message.equals("server_stopping")) { // Server is closing, client needs to stop
                    this.closeConfirmation = false;
                    System.out.println("[ThreadClient | Info] Server is stopping, client needs to stop too.");
                    this.close();
                } else if (message.equals("client_stop_ok")) { // Server confirmation for stopping client
                    System.out.println("[ThreadClient | Info] Server sended confirmation to stop.");
                } else {
                    if (!receiveMessage(message)) {
                        System.err.println("[ThreadClient | Error] Couldn't save the input.");
                    }
                }
            } catch (IOException e) {
                System.err.println("[ThreadClient | Error] \"" + e + "\".");
            }
        }
        System.out.println("[ThreadClient | Info] Client stopped.");
    }
    
    @Override
    public void close() {
        this.inService = false;
        this.client.LOnOff.setText("OFF");
        this.client.LOnOff.setForeground(Color.RED);
        this.client.BtnSeConnecterAuServeur.setText("Se connecter au serveur");
        try {
            if (this.closeConfirmation)
                this.sendMessage("client_stop_confirmation"); // Send closing alert to the server with the need of a confirmation (to unlock bufferRead.readLine())
            else
                this.sendMessage("client_stop_noconfirmation"); // Send closing alert to the server without needing confirmation
            if (this.bufferRead != null)
                this.bufferRead.close();
            if (this.bufferWrite != null)
                this.bufferWrite.close();
            if (this.clientSocket != null)
                this.clientSocket.close();
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] " + e);
        }
        System.out.println("[ThreadClient | Info] Stopping client...");
    }
    
    @Override
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

    @Override
    public synchronized boolean receiveMessage(String str) {
        if (this.messagesList.add(str)) {
            this.client.messageReceived();
            return true;
        }
        return false;
    }

    @Override
    public synchronized String getMessage() {
        if (!this.messagesList.isEmpty()) {
            String str = (String) this.messagesList.getFirst();
            this.messagesList.removeFirst();            
            return str;
        }
        return "";
    }

    @Override
    public synchronized String readMessage() {
        return (!this.messagesList.isEmpty()) ? (String) this.messagesList.getFirst() : "";
    }
    
    @Override
    public boolean getInService() {
        return this.inService;
    }
    
    // Getters and setters

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
