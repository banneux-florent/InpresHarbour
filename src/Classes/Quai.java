package Classes;

import Exceptions.AmarrageException;

/**
 *
 * @author Florent & Wadi
 */
public class Quai extends Amarrage {

    private MoyenDeTransportSurEau[] listeBateauxAmarres;
    private int placesOccupees = 0;
    private int places = 0;

    public Quai(String identifiant, int capacite, double longueurMaximum) {
        super(identifiant, capacite, longueurMaximum);
        listeBateauxAmarres = new MoyenDeTransportSurEau[capacite];
        setPlaces(capacite);
    }

    /**n,
     * @return the listeBateauxAmarres
     */
    public MoyenDeTransportSurEau[] getListeBateauxAmarres() {
        return listeBateauxAmarres;
    }

    /**
     * @param listeBateauxAmarres the listeBateauxAmarres to set
     */
    public void setListeBateauxAmarres(MoyenDeTransportSurEau[] listeBateauxAmarres) {
        this.listeBateauxAmarres = listeBateauxAmarres;
    }

    public void addMTSE(MoyenDeTransportSurEau mtse) throws AmarrageException {
        if (mtse instanceof BateauPeche) {
            BateauPeche bp = (BateauPeche) mtse;
            if (bp.getLongeur() > this.getLongueurMaximum()) {
                throw new AmarrageException("La longueur du bauteau excède la taille maximale.");
            }
        }
        if (getPlacesOccupees() < getPlaces()) {
            listeBateauxAmarres[getPlacesOccupees()] = mtse;
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
