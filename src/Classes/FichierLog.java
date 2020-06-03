package Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Florent & Wadi
 */
public class FichierLog {
    
    private String nomFichier = "";
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
            this.nomFichier = Fonctions.chargerConfig().getProperty("logs_filename");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void ecrireLigne(String ligne) {
        if (!path.equals("")) {
            try {
                FileWriter f = new FileWriter(path + nomFichier + ".txt", true);
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
    
}