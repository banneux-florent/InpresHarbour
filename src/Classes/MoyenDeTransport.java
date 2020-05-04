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
