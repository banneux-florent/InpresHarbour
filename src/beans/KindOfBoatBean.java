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

    private String id = "";
    private int lowerBound;
    private int upperBound;
    private int triggerMultiple;
    private int waitingTime;
    private ThreadRandomGenerator trg;
    private boolean isRunning = false;
    private Properties properties;
    private int numberRefPlaisance;
    private int numberRefPeche;
    private String kindOfBoat;

    private PropertyChangeSupport eventChange = new PropertyChangeSupport(this);

    public KindOfBoatBean() {
        System.err.println("[KoBB | Info] Kind of boat beans instanciated.");
        try {
            this.properties = Fonctions.chargerConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.lowerBound = Integer.parseInt(this.properties.getProperty("boatGenerator.lowerBound"));
        this.upperBound = Integer.parseInt(this.properties.getProperty("boatGenerator.upperBound"));
        this.triggerMultiple = 1;
        this.numberRefPeche = Integer.parseInt(this.properties.getProperty("boatGenerator.numberRefPeche"));
        this.numberRefPlaisance = Integer.parseInt(this.properties.getProperty("boatGenerator.numberRefPlaisance"));
        this.waitingTime = Integer.parseInt(this.properties.getProperty("boatGenerator.waitingTime"));
    }

    public void start() {
        if (isRunning()) {
            try {
                trg = new ThreadRandomGenerator(this, lowerBound, upperBound, triggerMultiple, waitingTime);
                trg.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        setIsRunning(false);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
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
        System.out.println("[KoBB | Info] Bean " + this.getId() + " is treating number " + n);
        if (n % numberRefPlaisance == 0) {
            // System.err.println("[KoBB | Info] (" + n + ") Bateau généré! (Bateau de plaisance)");
            notifyBoatDetected("Plaisance");
        } else if (n % numberRefPeche == 0) {
            // System.err.println("[KoBB | Info] (" + n + ") Bateau généré! (Bateau de p?che)");
            notifyBoatDetected("Peche");
        }
    }

    protected void notifyBoatDetected(String newKindOfBoat) {

        String oldKindOfBoat = this.getKindOfBoat();
        this.setKindOfBoat(newKindOfBoat);
        eventChange.firePropertyChange("KindOfBoat", oldKindOfBoat, newKindOfBoat);
        this.setIsRunning(false);
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
