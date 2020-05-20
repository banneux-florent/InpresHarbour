package Classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MoyenDeTransport implements AvecHumains {

    protected enum Energie {
        Essence,
        Diesel,
        Kerosene,
        Vapeur
    }
    
    private Energie energie;

    @Override
    public abstract int getNombreHumains();

    /**
     * @return the energie
     */
    public Energie getEnergie() {
        return energie;
    }

    /**
     * @param energie the energie to set
     */
    public void setEnergie(Energie energie) {
        this.energie = energie;
    }

}
