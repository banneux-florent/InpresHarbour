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

// To get trace of exception error
// Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, (String)null, exception);

/**
 *
 * @author Florent
 */
public class ThreadServer extends Thread implements NetworkThread {

    // Properties
    
    private NetworkBasicServer server = null;
    
    private Socket clientSocket = null;
    private ServerSocket serverSocket;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    private LinkedList<String> messagesList = new LinkedList<String>();
    private boolean inService = false;

    // Constructors
    
    public ThreadServer(NetworkBasicServer server) {
        this.server = server;
    }
    
    // Methods

    public void run() {
        System.out.println("[ThreadServer | Info] Connecting server...");
        this.inService = true;
        
        // Server's socket creation
        try {
            this.serverSocket = new ServerSocket(this.server.getPort());
        } catch (Exception e) {
            System.err.println("[ThreadServer | Error] Cloudn't create server socket. \"" + e + "\"");
            if (this.inService) this.close();
            return;
        }
        if (this.serverSocket == null) {
            System.err.println("[ThreadServer | Error] Server socket couln't be created.");
            if (this.inService) this.close();
            return;
        } else {
            System.out.println("[ThreadServer | Info] Server launched on port " + this.server.getPort());
            this.server.LOnOff.setText("ON");
            this.server.LOnOff.setForeground(Color.GREEN);
            this.server.BtnDemarrerServeur.setText("Arr?ter le serveur");
        }
        
        while (this.inService) {
            System.out.println("[ThreadServer | Info] Server waiting for client on port " + this.server.getPort());
            
            // Waiting for client to connect
            try {
                this.clientSocket = this.serverSocket.accept();
            } catch (SocketException e) {
                System.err.println("[ThreadServer | Error] Client socket acceptation interrupted.");
                if (this.inService) this.close();
                return;
            } catch (IOException e) {
                System.err.println("[ThreadServer | Error] Client socket couln't be accepted.");
                if (this.inService) this.close();
                return;
            } catch (Exception e) {
                System.err.println("[ThreadServer | Error] Error during client socket acceptation. \"" + e + "\"");
                if (this.inService) this.close();
                return;
            }
            if (this.clientSocket == null) {
                System.err.println("[ThreadServer | Error] Client socket couln't be accepted.");
                if (this.inService) this.close();
                return;
            } else {
                System.out.println("[ThreadServer | Info] The server accepted a connection.");
            }

            // Creating read/write buffers
            try {
                this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            } catch (IOException e) {
                System.err.println("[ThreadServer | Error] \"" + e + "\". Connection missing ?");
                if (this.inService) this.close();
                return;
            }
            if (this.bufferWrite == null || this.bufferRead == null) {
                System.err.println("[ThreadServer | Error] Read or write buffer couldn't be created.");
                if (this.inService) this.close();
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
                        System.out.println("[ThreadServer | Info] Client want to stop. Sending confirmation.");
                        this.sendMessage("client_stop_ok"); // Sending confirmation to client for stopping
                        break;
                    } else if (message.equals("client_stop_noconfirmation")) { // Client want to stop without confirmation
                        System.out.println("[ThreadServer | Info] Client is going to stop without confirmation.");
                        break;
                    } else {
                        if (!addMessage(message)) {
                            System.err.println("[ThreadServer | Error] Couldn't save the input.");
                        }
                    }
                } catch (Exception e) {
                    System.err.println("[ThreadClient | Error] \"" + e + "\".");
                }
            }
        }
        System.out.println("[ThreadServer | Info] Server stopped.");
    }
    
    // NetworkThread

    @Override
    public void close() {
        this.inService = false;
        this.server.LOnOff.setText("OFF");
        this.server.LOnOff.setForeground(Color.RED);
        this.server.BtnDemarrerServeur.setText("Démarrer le serveur");
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
        } catch (Exception e) {
            System.err.println("[ThreadServer | Error] " + e);
        }
        System.out.println("[ThreadServer | Info] Stopping server...");
    }

    @Override
    public void sendMessage(String message) {
        if (this.bufferWrite != null) {
            try {
                this.bufferWrite.write(message);
                this.bufferWrite.newLine();
                this.bufferWrite.flush();
                System.out.println("[ThreadServer | Info] [>>>] " + message);
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, (String)null, ex);
            }
        }
    }

    @Override
    public synchronized boolean addMessage(String str) {
        System.out.println(str);
        if (this.messagesList.add(str)) {
            this.server.messageReceived();
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
    public synchronized String[] getAllMessages() {
        return (String[])this.messagesList.toArray();
    }
    
    @Override
    public boolean getInService() {
        return this.inService;
    }
    
    // Getters and setters

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

    public BufferedReader getBufferRead() {
        return bufferRead;
    }

    public BufferedWriter getBufferWrite() {
        return bufferWrite;
    }

}
