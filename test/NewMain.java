
import Classes.Marin;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author WADI
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // chargement des propriétés
            Properties prop = Classes.Fonctions.chargerConfig("config.properties");
            System.out.println("ma.cle: " + prop.getProperty("port.ecoute"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
