package Network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;

class ThreadClient extends Thread {

    private String serverName;
    private int port;
    private Socket clientSocket = null;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    
    public ThreadClient(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }
    
    public void run() {
        try {
            this.clientSocket = new Socket(this.serverName, this.port);
            System.out.println("Client connecté : " + this.clientSocket.getInetAddress().toString());
        } catch (UnknownHostException e) {
            System.err.println("Erreur ! Host non trouvé [" + e + "]");
        } catch (IOException e) {
            System.err.println("Erreur ! Pas de connexion ? [" + e + "]");
        } 
        try {
            this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            System.out.println("Flux créé");
            if (this.clientSocket == null || this.bufferWrite == null)
                System.exit(1); 
        } catch (IOException e) {
            System.err.println("Erreur ! Pas de connexion ? [" + e + "]");
        } 
    }
    
    public String sendString(String s) {
        String response = null;
        try {
            this.bufferWrite.write(s);
            System.out.println("Write dans le thread");
            this.bufferWrite.newLine();
            this.bufferWrite.flush();
            System.out.println("Attente réponse dans le thread");
            response = this.bufferRead.readLine();
            System.out.println("Réponse reçue dans le thread");
        } catch (IOException ex) {
            Logger.getLogger(NetworkBasicClient.class.getName()).log(Level.SEVERE, (String)null, ex);
        } 
        return response;
    }
    
    public void sendStringWithoutWaiting(String s) {
        try {
            this.bufferWrite.write(s);
            this.bufferWrite.newLine();
            this.bufferWrite.flush();
        } catch (IOException ex) {
            Logger.getLogger(NetworkBasicClient.class.getName()).log(Level.SEVERE, (String)null, ex);
        } 
    }
    
    public String receiveString(String s) {
        String response = null;
        try {
            response = this.bufferRead.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, (String)null, ex);
        } 
        return response;
    }
    
    public void setEndSending() {
        try {
            sendStringWithoutWaiting("**EOC**");
            this.bufferWrite.close();
            this.bufferRead.close();
            this.clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkBasicClient.class.getName()).log(Level.SEVERE, (String)null, ex);
        } 
        System.out.println("Client déconnecté");
    }

}
