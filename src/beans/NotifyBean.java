/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Bateau;
import Classes.BateauPeche;
import Classes.BateauPlaisance;
import Classes.Equipage;
import Classes.Marin;
import Exceptions.SailorWithoutIdentificationException;
import Exceptions.ShipWithoutIdentificationException;
import Phare.ArrivageBateau;
import Phare.Phare;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Florent & Wadi
 */
public class NotifyBean implements BoatListener {
    
    private Phare phare;
    
    @Override
    public void BoatDetected(BoatEvent boatEvent) {
        // Affichage de la modal
        // Ajout du bateau dans la liste
        String[] countryCodes = Locale.getISOCountries();
        String pavillon = null;
        Random rand = new Random();
        int int_random = rand.nextInt(countryCodes.length);
        Locale l = new Locale("", countryCodes[int_random]);
        String country = l.getDisplayCountry();
        Bateau b = null;
        Equipage equipage = null;
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
        
        if (boatEvent.getBoatType() == BoatEvent.BoatType.Peche) {
            try {
                b = new BateauPeche(country, country, 0, 0, BateauPeche.TypeDePeche.Thonier, countryCodes[int_random]);
            } catch (ShipWithoutIdentificationException ex) {
                Logger.getLogger(NotifyBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                b = new BateauPlaisance(country, country, 0, 0, BateauPlaisance.TypePermis.PlaisanceOptionCotiere, countryCodes[int_random]);
            } catch (ShipWithoutIdentificationException ex) {
                Logger.getLogger(NotifyBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        b.setEquipage(equipage);
        ArrivageBateau arrivageBateau = new ArrivageBateau(b);
        this.getPhare().AddBoatNoIdentified(b);
        arrivageBateau.setVisible(true);
    }
    
    public NotifyBean() {
    }
    
    public Phare getPhare() {
        return phare;
    }
    
    public void setPhare(Phare phare) {
        this.phare = phare;
    }
    
}
