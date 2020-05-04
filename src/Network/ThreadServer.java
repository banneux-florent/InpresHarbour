package Network;

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
import javax.swing.JCheckBox;

class ThreadServeur extends Thread {

    private int port;
    private JCheckBox checkBoxCalled;
    private int callsCounter;
    private Socket clientSocket = null;
    private ServerSocket serverSocket;
    private BufferedReader bufferRead = null;
    private BufferedWriter bufferWrite = null;
    private LinkedList messagesList;
    private boolean inService = false;

    public ThreadServeur(int port, JCheckBox checkbox) {
        this.port = port;
        this.checkBoxCalled = checkbox;
        this.callsCounter = 0;
        this.messagesList = new LinkedList();
        this.inService = true;
    }

    public ThreadServeur(int port) {
        this.port = port;
        this.checkBoxCalled = null;
        this.callsCounter = 0;
        this.messagesList = new LinkedList();
    }

    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]");
            System.exit(1);
        }
        System.out.println("Serveur en attente sur le port " + this.port);
        try {
            this.clientSocket = this.serverSocket.accept();
        } catch (SocketException e) {
            System.err.println("Accept interrompu ! ? [" + e + "]");
        } catch (IOException e) {
            System.err.println("Erreur d'accept ! ? [" + e + "]");
            System.exit(1);
        }
        try {
            this.bufferRead = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferWrite = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            System.out.println("Flux créé");
            if (this.clientSocket == null || this.bufferWrite == null || this.bufferRead == null)
                System.exit(1); 
        } catch (IOException e) {
            System.err.println("Erreur ! Pas de connexion ? [" + e + "]");
        }
        String s = null;
        do {
            try {
                System.out.println("Attente d'une ligne ...");
                s = this.bufferRead.readLine();
                System.out.println("Ligne reçue = " + s);
                if (this.checkBoxCalled != null)
                    this.checkBoxCalled.setSelected(true); 
                this.callsCounter++;
                boolean b = putMessage(s);
                if (b) {
                    System.out.println("Ligne enregistrée = " + s);
                } else {
                    System.out.println("Échec enregistrement de : " + s);
                } 
            } catch (IOException ex) {
                Logger.getLogger(NetworkBasicServer.class.getName()).log(Level.SEVERE, (String)null, ex);
            } 
        } while (this.inService);
    }

    public synchronized boolean putMessage(String s) {
        return this.messagesList.add(s);
    }

    public synchronized String getMessage() {
        if (!this.messagesList.isEmpty()) {
            String s = (String) this.messagesList.getFirst();
            this.messagesList.removeFirst();
            this.callsCounter--;
            if (this.checkBoxCalled != null && this.callsCounter == 0)
                this.checkBoxCalled.setSelected(false); 
            return s;
        }
        return "";
    }

    public synchronized String[] getAllMessages() {
        return (String[])this.messagesList.toArray();
    }

    public void sendMessage(String m) {
        try {
            this.bufferWrite.write(m);
            this.bufferWrite.newLine();
            this.bufferWrite.flush();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
    }

    public void stopThread() {
        try {
            this.bufferRead.close();
            this.bufferWrite.close();
            this.clientSocket.close();
            this.serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
        System.out.println("Serveur arr?té !");
        this.inService = false;
        interrupt();
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
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

}
