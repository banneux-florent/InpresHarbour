/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

/**
 *
 * @author Florent & Wadi
 */
public class BoatBean implements PropertyChangeListener {

    private final LinkedList<BoatListener> listeners = new LinkedList<BoatListener>();
    private BoatEvent boatEvent = null;

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

        String kindOfBoat = (evt.getNewValue().toString());
        BoatEvent e = new BoatEvent(this); // génération de l'event
        if (kindOfBoat == "Plaisance") {
            e.setBoatType(BoatEvent.BoatType.Plaisance);
        } else {
            e.setBoatType(BoatEvent.BoatType.Peche);
        }
        int n = listeners.size();
        for (int i = 0; i < n; i++) // activation de la méthode AlertDetected pour chaque objet ? l'écoute
        {
            BoatListener obj = (BoatListener) listeners.get(i);
            obj.BoatDetected(e);
        }

    }

}
