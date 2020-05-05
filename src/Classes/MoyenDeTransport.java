package Classes;

/**
 *
 * @author Florent & Wadi
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
