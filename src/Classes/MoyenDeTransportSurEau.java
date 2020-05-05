package Classes;

/**
 *
 * @author Florent & Wadi
 */
public abstract class MoyenDeTransportSurEau extends MoyenDeTransport {

    private boolean submersible;

    /**
     * @return the submersible
     */
    public boolean isSubmersible() {
        return submersible;
    }

    /**
     * @param submersible the submersible to set
     */
    public void setSubmersible(boolean submersible) {
        this.submersible = submersible;
    }

}
