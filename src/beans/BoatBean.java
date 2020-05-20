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
public class BoatBean {
    
    private LinkedList<BoatListener> listeners = new LinkedList<BoatListener>();

    public BoatBean() {}
    
    public void addListener(BoatListener boatListener) {
        this.listeners.add(boatListener);
    }
    
    protected void notifyBoatDetected() {
        BoatEvent boatEvent = new BoatEvent(this);
        for (BoatListener boatListener : this.listeners) {
            boatListener.BoatDetected(boatEvent);
        }
    }
}
