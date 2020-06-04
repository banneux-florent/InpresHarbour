package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Florent & Wadi
 */
public class FichierLog {
    
    private String filename = "";
    private String path = "";
    
    public FichierLog() {
        String sep = System.getProperty("file.separator");
        path = System.getProperty("user.dir") + sep + "Donnees" + sep;
        File directory = new File(path);
        
        if (!directory.exists()) {
            path = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep;
            directory = new File(path);
            if (!directory.exists()) {
                path = "";
            }
        }
        try {
            this.filename = Fonctions.chargerConfig().getProperty("logs.filename");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void ecrireLigne(String ligne) {
        if (!path.equals("") && !filename.equals("")) {
            try {
                FileWriter f = new FileWriter(path + filename, true);
                BufferedWriter bf = new BufferedWriter(f);
                bf.write("[" + new Date() + "] " + ligne);
                bf.newLine();
                bf.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("[FichierLog | Error] Couldn't find data directory.");
        }
    }
    
    public LinkedList<String> lireLogs() {
        LinkedList<String> logs = new LinkedList<String>();
        if (!path.equals("") && !filename.equals("")) {
            try {
                FileReader f = new FileReader(path + filename);
                BufferedReader bf = new BufferedReader(f);
                String line;
                while ((line = bf.readLine()) != null) {
                    logs.add(line);
                }
                bf.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("[FichierLog | Error] Couldn't find data directory.");
        }
        return logs;
    }
    
}
