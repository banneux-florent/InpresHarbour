package beans;

import java.util.EventListener;

/**
 *
 * @author Florent & Wadi
 */
public interface BoatListener extends EventListener {

    public void BoatDetected(BoatEvent boatEvent);

}
