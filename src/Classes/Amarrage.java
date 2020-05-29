package Classes;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Amarrage implements AUnIdentifiant,Serializable {

    private double longueurMaximum;
    private int capacite;
    private String identifiant;

    public Amarrage(String identifiant, int capacite, double longueurMaximum) {
        this.capacite = capacite;
        this.identifiant = identifiant;
        this.longueurMaximum = longueurMaximum;
    }
    
    public Amarrage() {}

    /**
     * @return the capacite
     */
    public int getCapacite() {
        return capacite;
    }

    /**
     * @return the identifiant
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * @return the longueurMaximum
     */
    public double getLongueurMaximum() {
        return longueurMaximum;
    }

    /**
     * @param longueurMaximum the longueurMaximum to set
     */
    public void setLongueurMaximum(double longueurMaximum) {
        this.longueurMaximum = longueurMaximum;
    }

}
