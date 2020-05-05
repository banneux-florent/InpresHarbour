package Network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

class ThreadServeur extends Thread {

    // Properties
    
    private int port;
    private Socket clientSocket = null;
    private ServerSocket serverSocket;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    private LinkedList messagesList = new LinkedList();
    private boolean inService = false;
    private JLabel lOnOff = null;
    private JButton BtnDemarrerServeur = null;

    // Constructors
    
    public ThreadServeur(int port, JLabel lOnOff, JButton BtnDemarrerServeur) throws Exception {
        this.setPort(port);
        this.lOnOff = lOnOff;
        this.BtnDemarrerServeur = BtnDemarrerServeur;
    }
    
    // Methods

    public void run() {
        this.inService = true;
        
        // Server's socket creation
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("[ThreadServer | Error] Cloudn't create server socket. \"" + e + "\"");
            this.close();
            return;
        }
        if (this.serverSocket == null) {
            System.err.println("[ThreadServer | Error] Server socket couln't be created.");
            this.close();
            return;
        } else {
            System.out.println("[ThreadServer | Info] Server launched on port " + this.port);
            this.lOnOff.setText("ON");
            this.lOnOff.setForeground(Color.GREEN);
            this.BtnDemarrerServeur.setText("Arr?ter le serveur");
        }
        
        while (this.inService) {
            System.out.println("[ThreadServer | Info] Server waiting for client on port " + this.port);
            
            // Waiting for client to connect
            try {
                this.clientSocket = this.serverSocket.accept();
            } catch (SocketException e) {
                System.err.println("[ThreadServer | Error] Client socket acceptation interrupted.");
                this.close();
                return;
            } catch (IOException e) {
                System.err.println("[ThreadServer | Error] Client socket couln't be accepted.");
                this.close();
                return;
            } catch (Exception e) {
                System.err.println("[ThreadServer | Error] Error during client socket acceptation.");
                this.close();
                return;
            }
            if (this.clientSocket == null) {
                System.err.println("[ThreadServer | Error] Client socket couln't be accepted.");
                this.close();
                return;
            } else {
                System.out.println("[ThreadServer | Info] The server accepted a connection.");
            }

            // Creating read/write buffers
            try {
                this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            } catch (IOException e) {
                System.err.println("[ThreadServer | Error] \"" + e + "\" (Connection missing ?)");
                this.close();
                return;
            }
            if (this.bufferWrite == null || this.bufferRead == null) {
                System.err.println("[ThreadServer | Error] Read or write buffer couldn't be created.");
                this.close();
                return;
            } else {
                System.out.println("[ThreadServer | Info] Stream created.");
            }

            // Server running loop
            String message = null;
            while (this.inService) {
                try {
                    System.out.println("[ThreadServer | Info] Waiting for an input...");
                    message = this.bufferRead.readLine();
                    System.out.println("[ThreadServer | Info] [<<<] " + message);    
                    if (message.equals("client_stop_confirmation")) { // Client want to stop
                        this.sendMessage("client_stop_ok"); // Sending confirmation to client for stopping
                        System.out.println("[ThreadServer | Info] Client want to stop.");
                        break;
                    } else if (message.equals("client_stop_noconfirmation")) { // Client want to stop without confirmation
                        System.out.println("[ThreadServer | Info] Client is going to stop without confirmation.");
                        break;
                    } else {
                        if (!addMessage(message)) {
                            System.err.println("[ThreadServer | Error] Couldn't save the input.");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NetworkBasicServer.class.getName()).log(Level.SEVERE, (String)null, ex);
                }
            }
        }
        System.out.println("[ThreadServer | Info] Server stopped.");
    }

    public void close() {
        this.inService = false;
        this.lOnOff.setText("OFF");
        this.lOnOff.setForeground(Color.RED);
        this.BtnDemarrerServeur.setText("Démarrer le serveur");
        this.sendMessage("server_stopping");
        try {
            if (this.bufferRead != null)
                this.bufferRead.close();
            if (this.bufferWrite != null)
                this.bufferWrite.close();
            if (this.clientSocket != null)
                this.clientSocket.close();
            if (this.serverSocket != null)
                this.serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, (String)null, ex);
        } catch (Exception e) {
            System.err.println("[ThreadServer | Error] " + e);
        }
        System.out.println("[ThreadServer | Info] Stopping server...");
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
                System.out.println("[ThreadServer | Info] [>>>] " + message);
            } catch (IOException ex) {
                Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, (String)null, ex);
            }
        }
    }
    
    // Getters and setters
    
    public int getPort() {
        return this.port;
    }

    public void setPort(int port) throws Exception {
        if (port < 0)
            throw new Exception("[ThreadServer | Error] Port must be a positive integer.");
        this.port = port;
    }

    public Socket getClientSocket() {
        return this.clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public boolean getInService() {
        return this.inService;
    }

}
