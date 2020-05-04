/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.ShipWithoutIdentificationException;

/**
 *
 * @author WADI
 */
public class BateauPlaisance extends Bateau {

    public static enum TypePermis {
        PlaisanceOptionCotiere,
        PlaisanceExtentionHauturiere
    }

    private TypePermis typePermis;

    public BateauPlaisance( String nom, String portAttache, int tonnage, float longeur,TypePermis typePermis,String pavillon) throws ShipWithoutIdentificationException{
        super(nom, portAttache, tonnage, longeur,pavillon);
        this.typePermis = typePermis;
    }

    /**
     * @return the typePermis
     */
    public TypePermis getTypePermis() {
        return typePermis;
    }

    /**
     * @param typePermis the typePermis to set
     */
    public void setTypePermis(TypePermis typePermis) {
        this.typePermis = typePermis;
    }

    @Override
    public String toString() {
        return "BateauPlaisance / "+ super.toString();
    }

}
