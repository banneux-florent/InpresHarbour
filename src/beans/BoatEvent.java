/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDateTime;
import java.util.EventObject;
import java.util.LinkedList;

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
    
    public BoatEvent(Object source) {
        super(source);
    }
    
}
