package Network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final LinkedList<String> messagesList = new LinkedList<>();
    private boolean inService = false;

    // Constructors
    
    public ThreadServer(NetworkBasicServer server) {
        this.server = server;
    }
    
    // Methods

    @Override
    public void run() {
        System.out.println("[ThreadServer | Info] Connecting server...");
        this.inService = true;
        
        // Server's socket creation
        try {
            this.serverSocket = new ServerSocket(this.server.getPort());
        } catch (IOException e) {
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
            this.server.BtnDemarrerServeur.setText("Arrêter le serveur");
        }
        
        // Server running loop
        while (this.inService) {
            System.out.println("[ThreadServer | Info] Server waiting for client on port " + this.server.getPort());
            
            // Waiting for a single client to connect
            try {
                this.clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                System.err.println("[ThreadServer | Error] Client socket couln't be accepted. \"" + e + "\"");
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

            // Getting client's socket's read/write buffers
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
                System.out.println("[ThreadServer | Info] Client socket stream retrived.");
            }

            // Listening client connected loop
            String message;
            while (true) {
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
                        if (!receiveMessage(message)) {
                            System.err.println("[ThreadServer | Error] Couldn't save the input.");
                        }
                    }
                } catch (IOException e) {
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
        } catch (IOException e) {
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
    public synchronized boolean receiveMessage(String str) {
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
