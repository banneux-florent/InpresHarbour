package Classes;

import Exceptions.AmarrageException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ponton extends Amarrage {

    private MoyenDeTransportSurEau[] listeMTSEAmarresCote1;
    private int placesOccupeesCote1 = 0;
    private int placesCote1 = 0;
    private MoyenDeTransportSurEau[] listeMTSEAmarresCote2;
    private int placesOccupeesCote2 = 0;
    private int placesCote2 = 0;

    public Ponton(String identifiant, int capacite, double longueurMaximum) {
        super(identifiant, capacite, longueurMaximum);
        setPlacesCote1((capacite / 2) + (capacite % 2));
        listeMTSEAmarresCote1 = new MoyenDeTransportSurEau[getPlacesCote1()];
        setPlacesCote2(capacite / 2);
        listeMTSEAmarresCote2 = new MoyenDeTransportSurEau[getPlacesCote2()];
    }
    
    public Ponton() {}

    public MoyenDeTransportSurEau[] getListe(int cote) {
        return (cote == 1) ? listeMTSEAmarresCote1 : listeMTSEAmarresCote2;
    }

    public void addMTSE(MoyenDeTransportSurEau mtse) throws AmarrageException {
        if (mtse instanceof BateauPlaisance) {
            BateauPlaisance bp = (BateauPlaisance) mtse;
            if (bp.getLongeur() > this.getLongueurMaximum()) {
                throw new AmarrageException("La longueur du bauteau exc?de la taille maximale.");
            }
        }
        if (getPlacesOccupeesCote1() < getPlacesCote1()) {
            listeMTSEAmarresCote1[getPlacesOccupeesCote1()] = mtse;
            placesOccupeesCote1++;
        } else if (getPlacesOccupeesCote2() < getPlacesCote2()) {
            listeMTSEAmarresCote2[getPlacesOccupeesCote2()] = mtse;
            placesOccupeesCote2++;
        } else {
            throw new AmarrageException("Il n'y a plus de places dans ce ponton.");
        }
    }

    public MoyenDeTransportSurEau[] getListeMTSEAmarresCote1() {
        return listeMTSEAmarresCote1;
    }

    public void setListeMTSEAmarresCote1(MoyenDeTransportSurEau[] listeMTSEAmarresCote1) {
        this.listeMTSEAmarresCote1 = listeMTSEAmarresCote1;
    }

    public MoyenDeTransportSurEau[] getListeMTSEAmarresCote2() {
        return listeMTSEAmarresCote2;
    }

    public void setListeMTSEAmarresCote2(MoyenDeTransportSurEau[] listeMTSEAmarresCote2) {
        this.listeMTSEAmarresCote2 = listeMTSEAmarresCote2;
    }
    
    

    /**
     * @return the placesCote1
     */
    public int getPlacesCote1() {
        return placesCote1;
    }

    /**
     * @param placesCote1 the placesCote1 to set
     */
    public void setPlacesCote1(int placesCote1) {
        this.placesCote1 = placesCote1;
    }

    /**
     * @return the placesCote2
     */
    public int getPlacesCote2() {
        return placesCote2;
    }

    /**
     * @param placesCote2 the placesCote2 to set
     */
    public void setPlacesCote2(int placesCote2) {
        this.placesCote2 = placesCote2;
    }

    /**
     * @return the placesOccupeesCote1
     */
    public int getPlacesOccupeesCote1() {
        return placesOccupeesCote1;
    }

    /**
     * @param placesOccupeesCote1 the placesOccupeesCote1 to set
     */
    public void setPlacesOccupeesCote1(int placesOccupeesCote1) {
        this.placesOccupeesCote1 = placesOccupeesCote1;
    }

    /**
     * @return the placesOccupeesCote2
     */
    public int getPlacesOccupeesCote2() {
        return placesOccupeesCote2;
    }

    /**
     * @param placesOccupeesCote2 the placesOccupeesCote2 to set
     */
    public void setPlacesOccupeesCote2(int placesOccupeesCote2) {
        this.placesOccupeesCote2 = placesOccupeesCote2;
    }

}
