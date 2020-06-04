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
    private boolean showMessages = true;
    private int waitingTime;
    private int lowerBound;
    private int upperBound;
    private int triggerMultiple;
    private ThreadRandomGenerator trg;
    private boolean isRunning = false;
    private int numberRefPlaisance;
    private int numberRefPeche;
    private String kindOfBoat;

    private PropertyChangeSupport eventChange = new PropertyChangeSupport(this);

    public KindOfBoatBean() {
        if (this.showMessages)
            System.err.println("[KoBB | Info] Kind of boat beans instanciated.");
        
        Properties properties = new Properties();
        try {
            properties = Fonctions.chargerConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.showMessages = Integer.parseInt(properties.getProperty("boatGenerator.showMessages")) == 1;
        this.waitingTime = Integer.parseInt(properties.getProperty("boatGenerator.waitingTime"));
        this.lowerBound = Integer.parseInt(properties.getProperty("boatGenerator.lowerBound"));
        this.upperBound = Integer.parseInt(properties.getProperty("boatGenerator.upperBound"));
        this.triggerMultiple = 1;
        this.numberRefPeche = Integer.parseInt(properties.getProperty("boatGenerator.numberRefPeche"));
        this.numberRefPlaisance = Integer.parseInt(properties.getProperty("boatGenerator.numberRefPlaisance"));
    }

    public void start() {
        if (isRunning()) {
            try {
                trg = new ThreadRandomGenerator(this, showMessages, lowerBound, upperBound, triggerMultiple, waitingTime);
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
        if (this.showMessages)
            System.out.println("[KoBB | Info] Bean " + this.getId() + " is treating number " + n);
        
        if (n % numberRefPlaisance == 0) {
            // System.err.println("[KoBB | Info] (" + n + ") Bateau généré! (Bateau de plaisance)");
            notifyBoatDetected("Plaisance");
        } else if (n % numberRefPeche == 0) {
            // System.err.println("[KoBB | Info] (" + n + ") Bateau généré! (Bateau de pêche)");
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
