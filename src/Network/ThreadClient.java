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

class ThreadClient extends Thread {
    
    // Properties

    private String serverName;
    private int port = -1;
    private Socket clientSocket = null;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    private LinkedList messagesList = new LinkedList();
    private boolean inService = false;
    private JLabel lOnOff = null;
    private JButton BtnSeConnecterAuServeur = null;
    
    // Constructors
    
    public ThreadClient(String serverName, int port, JLabel lOnOff, JButton BtnSeConnecterAuServeur) throws Exception {
        this.setServerName(serverName);
        this.setPort(port);
        this.lOnOff = lOnOff;
        this.BtnSeConnecterAuServeur = BtnSeConnecterAuServeur;
    }
    
    // Methods
    
    public void run() {
        System.out.println("[ThreadClient | Info] Client connecting...");
        this.inService = true;
        
        // Client's socket creation
        try {
            this.clientSocket = new Socket(this.serverName, this.port);
        } catch (UnknownHostException e) {
            System.err.println("[ThreadClient | Error] Host unknown.");
            this.close(true);
            return;
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] \"" + e + "\" (Connection missing ?)");
            this.close(true);
            return;
        }
        if (this.clientSocket == null) {
            System.err.println("[ThreadClient | Error] Client socket couln't be created.");
            this.close(true);
            return;
        } else {
            System.out.println("[ThreadClient | Info] Client connected: " + this.clientSocket.getInetAddress().toString());
            this.lOnOff.setText("ON");
            this.lOnOff.setForeground(Color.GREEN);
            this.BtnSeConnecterAuServeur.setText("Se déconnecter du serveur");
        }
        
        // Creating read/write buffers
        try {
            this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("[ThreadClient | Error] \"" + e + "\" (Connection missing ?)");
            this.close(true);
            return;
        }
        if (this.bufferRead == null || this.bufferWrite == null) {
            System.err.println("[ThreadClient | Error] Read or write buffer couldn't be created.");
            this.close(true);
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
                    if (addMessage(message)) {
                        System.out.println("[ThreadClient | Info] Input saved.");
                    } else {
                        System.err.println("[ThreadClient | Error] Couldn't save the input.");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(NetworkBasicClient.class.getName()).log(Level.SEVERE, (String)null, ex);
            }
        } while (this.inService);
        System.out.println("[ThreadClient | Info] Client stopped.");
    }
    
    public void close(boolean confirmation) {
        this.inService = false;
        this.lOnOff.setText("OFF");
        this.lOnOff.setForeground(Color.RED);
        this.BtnSeConnecterAuServeur.setText("Se connecter au serveur");
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
        } catch (IOException ex) {
            Logger.getLogger(NetworkBasicClient.class.getName()).log(Level.SEVERE, (String)null, ex);
        } catch (Exception e) {
            System.err.println("[ThreadClient | Error] " + e);
        }
        System.out.println("[ThreadClient | Info] Stopping client...");
    }

    public synchronized boolean addMessage(String str) {
        return this.messagesList.add(str);
    }

    public synchronized String getMessage() {
        if (!this.messagesList.isEmpty()) {
            String str = (String) this.messagesList.getFirst();
            this.messagesList.removeFirst();            
            return str;
        }
        return "";
    }

    public synchronized String[] getAllMessages() {
        return (String[])this.messagesList.toArray();
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
    
    public String getServerName() {
        return this.serverName;
    }
    
    public final void setServerName(String serverName) throws Exception {
        if (serverName.isEmpty())
            throw new Exception("[ThreadClient | Error] Server name can't be empty.");
        this.serverName = serverName;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(int port) throws Exception {
        if (port < 0)
            throw new Exception("[ThreadClient | Error] Port must be a positive integer.");
        this.port = port;
    }
    
    public boolean getInService() {
        return this.inService;
    }

}
