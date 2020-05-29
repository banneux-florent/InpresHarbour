package Classes;

import Exceptions.AmarrageException;
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
public class Quai extends Amarrage implements Serializable {

    private MoyenDeTransportSurEau[] listeMTSEAmarres;
    private int placesOccupees = 0;
    private int places = 0;

    public Quai(String identifiant, int capacite, double longueurMaximum) {
        super(identifiant, capacite, longueurMaximum);
        listeMTSEAmarres = new MoyenDeTransportSurEau[capacite];
        setPlaces(capacite);
    }
    
    public Quai() {}

    /**n,
     * @return the listeMTSEAmarres
     */
    public MoyenDeTransportSurEau[] getListeBateauxAmarres() {
        return listeMTSEAmarres;
    }

    /**
     * @param listeBateauxAmarres the listeMTSEAmarres to set
     */
    public void setListeBateauxAmarres(MoyenDeTransportSurEau[] listeBateauxAmarres) {
        this.listeMTSEAmarres = listeBateauxAmarres;
    }

    public void addMTSE(MoyenDeTransportSurEau mtse) throws AmarrageException {
        if (mtse instanceof BateauPeche) {
            BateauPeche bp = (BateauPeche) mtse;
            if (bp.getLongeur() > this.getLongueurMaximum()) {
                throw new AmarrageException("La longueur du bauteau excède la taille maximale.");
            }
        }
        if (getPlacesOccupees() < getPlaces()) {
            listeMTSEAmarres[getPlacesOccupees()] = mtse;
            placesOccupees++;
        } else {
            // Plus de places

        }
    }

    /**
     * @return the placesOccupees
     */
    public int getPlacesOccupees() {
        return placesOccupees;
    }

    /**
     * @param placesOccupees the placesOccupees to set
     */
    public void setPlacesOccupees(int placesOccupees) {
        this.placesOccupees = placesOccupees;
    }

    /**
     * @return the places
     */
    public int getPlaces() {
        return places;
    }

    /**
     * @param places the places to set
     */
    public void setPlaces(int places) {
        this.places = places;
    }

}
