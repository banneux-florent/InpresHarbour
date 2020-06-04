package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florent & Wadi
 */
public class Fonctions {

    public static LinkedList<String[]> getPays() {
        return readCsv("pays");
    }

    public static Hashtable<String, String> getUtilisateurs() {
        LinkedList<String[]> utilisateursCsv = readCsv("utilisateurs");
        Hashtable<String, String> utilisateurs = new Hashtable<String, String>();
        for (int i = 0; i < utilisateursCsv.size(); i++) {
            utilisateurs.put(utilisateursCsv.get(i)[0], utilisateursCsv.get(i)[1]);
        }
        return utilisateurs;
    }

    public static void addUtilisateur(String utilisateur, String motDePasse) {
        writeCsv("utilisateurs", utilisateur + ";" + motDePasse + System.getProperty("line.separator"));
    }

    public static void writeCsv(String filename, String data) {
        String sep = System.getProperty("file.separator");
        String csvFile = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + filename + ".csv";
        if (!Fonctions.fileExists(csvFile)) {
            csvFile = System.getProperty("user.dir") + sep + "Donnees" + sep + filename + ".csv";
            if (!Fonctions.fileExists(csvFile)) {
                System.out.println("Le fichier « " + filename + " » n'existe pas ");
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(csvFile), true);
            os.write(data.getBytes(), 0, data.length());
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean fileExists(String filename) {
        try {
            OutputStream os = new FileOutputStream(new File(filename), true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static String getFilePath(String filename) {
        String sep = System.getProperty("file.separator");
        String filePath = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + filename;
        if (!Fonctions.fileExists(filePath)) {
            filePath = System.getProperty("user.dir") + sep + "Donnees" + sep + filename;
            if (!Fonctions.fileExists(filePath)) {
                System.out.println("Le fichier « " + filename + " » n'existe pas.");
                return "";
            }
        }
        return filePath;
    }

    public static LinkedList<String[]> readCsv(String filename) {
        LinkedList<String[]> donnees = new LinkedList<String[]>();

        String sep = System.getProperty("file.separator");
        String csvFile = Fonctions.getFilePath(filename + ".csv");
        
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                donnees.add(line.split(cvsSplitBy));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return donnees;
    }

    public static Properties chargerConfig() throws IOException, FileNotFoundException {

        String sep = System.getProperty("file.separator");
        String file = System.getProperty("user.dir") + sep + "src" + sep + "Donnees" + sep + "config.properties";
        if (!Fonctions.fileExists(file)) {
            file = System.getProperty("user.dir") + sep + "Donnees" + sep + "config.properties";
            if (!Fonctions.fileExists(file)) {
                System.out.println("Le fichier « config.properties « n'existe pas.");
                return null;
            }
        }

        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(file);
        try {
            properties.load(input);
            return properties;
        } finally {
            input.close();
        }
    }

}
