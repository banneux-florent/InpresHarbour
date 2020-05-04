/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author WADI
 */
public abstract class Amarrage implements AUnIdentifiant {

    private double longueurMaximum;
    private int capacite;
    private String identifiant;

    public Amarrage(String identifiant, int capacite, double longueurMaximum) {
        this.capacite = capacite;
        this.identifiant = identifiant;
        this.longueurMaximum = longueurMaximum;
    }

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
