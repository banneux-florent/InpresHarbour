/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.LinkedList;

/**
 *
 * @author Florent & Wadi
 */
public class BoatBean implements BoatListener {
    
    private final LinkedList<BoatListener> listeners = new LinkedList<BoatListener>();
    private BoatEvent boatEvent = null;

    public BoatBean() {}
    
    public void addListener(BoatListener boatListener) {
        this.listeners.add(boatListener);
    }
    
    public void removeListener(BoatListener boatListener) {
        this.listeners.remove(boatListener);
    }
    
    @Override
    public void BoatDetected(BoatEvent boatEvent) {
        int lowerBound = 0, upperBound = 5;
        int generatedNumber = (int) (lowerBound + Math.random()*(upperBound - lowerBound));
        this.boatEvent = boatEvent;
        this.boatEvent.setFlag(generatedNumber);
        notifyBoatDetected();
    }
    
    protected void notifyBoatDetected() {
        BoatEvent boatEvent = new BoatEvent(this.boatEvent); // Pour éviter des bugs de référence
        for (BoatListener boatListener : this.listeners) {
            boatListener.BoatDetected(boatEvent);
        }
    }
    
}
