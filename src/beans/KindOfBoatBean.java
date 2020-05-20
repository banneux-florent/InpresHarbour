/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.BoatEvent.BoatType;
import java.util.LinkedList;

/**
 *
 * @author Florent & Wadi
 */
public class KindOfBoatBean implements IUserNumber {
    
    private String id;
    private LinkedList<BoatListener> listeners = new LinkedList<BoatListener>();
    private BoatEvent boatEvent = new BoatEvent(this);
    
    public KindOfBoatBean(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void processNumber(int n) {
        // Set le type en fonction de n du coup..?
        this.boatEvent.setBoatType(BoatType.Plaisance);
        notifyBoatDetected();
    }
    
    public void addListener(BoatListener boatListener) {
        this.listeners.add(boatListener);
    }
    
    public void removeListener(BoatListener boatListener) {
        this.listeners.remove(boatListener);
    }
    
    protected void notifyBoatDetected() {
        BoatEvent boatEvent = new BoatEvent(this.boatEvent); // Pour éviter des bugs de référence
        for (BoatListener boatListener : this.listeners) {
            boatListener.BoatDetected(boatEvent);
        }
    }
    
}
