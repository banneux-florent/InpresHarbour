/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.EventObject;

/**
 *
 * @author Florent & Wadi
 */
public class BoatEvent extends EventObject {

    public static enum BoatType {
        Plaisance,
        Peche
    }

    private LocalDateTime receptedAt = null;
    private BoatType boatType = null;
    private int flag = -1;

    public BoatEvent(Object source) { // Default constructor
        super(source);
        this.receptedAt = LocalDateTime.now();
    }

    public BoatEvent(Object source, BoatType boatType, int flag) { // Init constructor
        super(source);
        this.receptedAt = LocalDateTime.now();
        this.boatType = boatType;
        this.flag = flag;
    }

    public LocalDateTime getReceptedAt() {
        return receptedAt;
    }

    public void setReceptedAt(LocalDateTime receptedAt) {
        this.receptedAt = receptedAt;
    }

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        if (flag > 0) {
            this.flag = flag;
        }
    }

}
