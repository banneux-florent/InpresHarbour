package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

/**
 *
 * @author Florent & Wadi
 */
public class BoatBean implements PropertyChangeListener {

    private final LinkedList<BoatListener> listeners = new LinkedList<>();

    public BoatBean() {
    }

    public void addListener(BoatListener boatListener) {
        this.listeners.add(boatListener);
    }

    public void removeListener(BoatListener boatListener) {
        this.listeners.remove(boatListener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String kindOfBoat = evt.getNewValue().toString();
        BoatEvent boatEvent = new BoatEvent(this);
        if (kindOfBoat == "Plaisance") {
            boatEvent.setBoatType(BoatEvent.BoatType.Plaisance);
        } else {
            boatEvent.setBoatType(BoatEvent.BoatType.Peche);
        }
        for (int i = 0; i < listeners.size(); i++) { // D�clenchement de AlertDetected pour chaque listeners
            listeners.get(i).BoatDetected(boatEvent);
        }
    }

}
