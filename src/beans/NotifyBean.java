package beans;

import Classes.Bateau;
import Classes.BateauPeche;
import Classes.BateauPlaisance;
import Classes.Equipage;
import Classes.FichierLog;
import Classes.Fonctions;
import Classes.Marin;
import Exceptions.ShipWithoutIdentificationException;
import Phare.ArrivageBateau;
import Phare.Phare;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florent & Wadi
 */
public class NotifyBean implements BoatListener {
    
    private Phare phare;
    private LinkedList<String[]> countries = Fonctions.getPays();
    
    public NotifyBean() {}
    
    @Override
    public void BoatDetected(BoatEvent boatEvent) {
        // Ajout du bateau dans la liste
        int rand = (new Random()).nextInt(countries.size());
        String[] country = countries.get(rand);
        
        Equipage equipage = new Equipage();
        try {
            Marin capitaine = new Marin("Mokh", "Wad", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Capitaine);
            Marin second = new Marin("Flo", "Bann", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Second);
            Marin bosco = new Marin("Air", "29", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.Bosco);
            Marin mecanicien = new Marin("Oussama", "Achour", LocalDate.of(2014, Month.JANUARY, 1), Marin.Fonction.MaitreMecanicien);
            
            equipage = new Equipage(capitaine, second);
            equipage.getMarins().add(bosco);
            equipage.getMarins().add(mecanicien);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        Bateau newBateau = new Bateau();
        if (boatEvent.getBoatType() == BoatEvent.BoatType.Peche) {
            try {
                newBateau = new BateauPeche(country[1], country[1], 0, 0, BateauPeche.TypeDePeche.Thonier, country[0]);
            } catch (ShipWithoutIdentificationException ex) {
                Logger.getLogger(NotifyBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                newBateau = new BateauPlaisance(country[1], country[1], 0, 0, BateauPlaisance.TypePermis.PlaisanceOptionCotiere, country[0]);
            } catch (ShipWithoutIdentificationException ex) {
                Logger.getLogger(NotifyBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newBateau.setEquipage(equipage);
        
        FichierLog fl = new FichierLog();
        fl.ecrireLigne("[NotifyBean | Info] Un nouveau bateau a été généré: " + newBateau.toString());
        try {
            if (Integer.parseInt(Fonctions.chargerConfig().getProperty("boatGenerator.showMessages")) == 1) {
                System.err.println("[NotifyBean | Info] Un nouveau bateau a été généré: " + newBateau.toString());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        ArrivageBateau arrivageBateau = new ArrivageBateau(newBateau);
        this.getPhare().AddBoatNoIdentified(newBateau);
        
        // Affichage de la fen?tre
        arrivageBateau.setVisible(true);
    }
    
    public Phare getPhare() {
        return phare;
    }
    
    public void setPhare(Phare phare) {
        this.phare = phare;
    }
    
}
