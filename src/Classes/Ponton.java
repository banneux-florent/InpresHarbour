/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.AmarrageException;

/**
 *
 * @author WADI
 */
public class Ponton extends Amarrage {

    private MoyenDeTransportSurEau[] listeMTSEPontonCote1;
    private int placesOccupeesCote1 = 0;
    private int placesCote1 = 0;
    private MoyenDeTransportSurEau[] listeMTSEPontonCote2;
    private int placesOccupeesCote2 = 0;
    private int placesCote2 = 0;

    public Ponton(String identifiant, int capacite, double longueurMaximum) {
        super(identifiant, capacite, longueurMaximum);
        setPlacesCote1((capacite / 2) + (capacite % 2));
        listeMTSEPontonCote1 = new MoyenDeTransportSurEau[getPlacesCote1()];
        setPlacesCote2(capacite / 2);
        listeMTSEPontonCote2 = new MoyenDeTransportSurEau[getPlacesCote2()];
    }

    public MoyenDeTransportSurEau[] getListe(int cote) {
        return (cote == 1) ? listeMTSEPontonCote1 : listeMTSEPontonCote2;
    }

    public void addMTSE(MoyenDeTransportSurEau mtse) throws AmarrageException {
        if (mtse instanceof BateauPlaisance) {
            BateauPlaisance bp = (BateauPlaisance) mtse;
            if (bp.getLongeur() > this.getLongueurMaximum()) {
                throw new AmarrageException("La longueur du bauteau excède la taille maximale.");
            }
        }
        if (getPlacesOccupeesCote1() < getPlacesCote1()) {
            listeMTSEPontonCote1[getPlacesOccupeesCote1()] = mtse;
            placesOccupeesCote1++;
        } else if (getPlacesOccupeesCote2() < getPlacesCote2()) {
            listeMTSEPontonCote2[getPlacesOccupeesCote2()] = mtse;
            placesOccupeesCote2++;
        } else {
            // Plus de places

        }
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
