/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Fonctions;
import beans.BoatEvent.BoatType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Properties;
import threadsutils.ThreadRandomGenerator;

/**
 *
 * @author Florent & Wadi
 */
public class KindOfBoatBean implements IUserNumber {

    private  String id;
    private int lowerBound;
    private int upperBound;
    private int triggerMultiple;
    private int waitingTime;
    private ThreadRandomGenerator trg;
    private boolean enMarche;
    private Properties properties;
    private int numberRefPlaisance;
    private int numberRefPeche;
    private String kindOfBoat;

    private PropertyChangeSupport eventChange = new PropertyChangeSupport(this);

    public KindOfBoatBean() {
        this.id = "";
        setEnMarche(false);
        System.err.println("Kind of boat beans instanciate");
        try {
            this.properties = Fonctions.chargerConfig();

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lowerBound = Integer.parseInt(this.properties.getProperty("lowerBound"));
        this.upperBound = Integer.parseInt(this.properties.getProperty("upperBound"));
        this.triggerMultiple = 1;
        this.numberRefPeche = Integer.parseInt(this.properties.getProperty("numberRefPeche"));
        this.numberRefPlaisance = Integer.parseInt(this.properties.getProperty("numberRefPlaisance"));
        this.waitingTime = Integer.parseInt(this.properties.getProperty("waitingTime"));
    }

    public void start() {
        if (isEnMarche()) {
            try {
                trg = new ThreadRandomGenerator(this, lowerBound, upperBound, triggerMultiple, waitingTime);
                trg.start();
            } catch (Exception e) {
            }

        }
    }

    public void stop() {
        setEnMarche(false);
    }

    public boolean isEnMarche() {
        return enMarche;
    }

    public void setEnMarche(boolean enMarche) {
        this.enMarche = enMarche;
    }

    public String getKindOfBoat() {
        return kindOfBoat;
    }

    public void setKindOfBoat(String kindOfBoat) {
        this.kindOfBoat = kindOfBoat;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void processNumber(int n) {
        // Set le type en fonction de n du coup..?
        System.err.println("Kinf of boat beans nombre generer" + n);
        if (n % numberRefPlaisance == 0) {
            //this.boatEvent.setBoatType(BoatType.Plaisance);
            System.err.println("Kob Bateau plaisance");
            notifyBoatDetected("Plaisance");
        } else if (n % numberRefPeche == 0) {
            //this.boatEvent.setBoatType(BoatType.Peche);
            System.err.println("Kob Bateau peche");
            notifyBoatDetected("Peche");
        }

    }

    protected void notifyBoatDetected(String newKindOfBoat) {

        String oldKindOfBoat = this.getKindOfBoat();
        this.setKindOfBoat(newKindOfBoat);
        eventChange.firePropertyChange("KindOfBoat", oldKindOfBoat, newKindOfBoat);
        this.setEnMarche(false);
        trg.stop();
        return;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        eventChange.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        eventChange.removePropertyChangeListener(l);
    }

}
