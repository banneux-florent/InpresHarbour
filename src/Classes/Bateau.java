package Classes;

import Exceptions.ShipWithoutIdentificationException;

/**
 *
 * @author Florent & Wadi
 */
public class Bateau extends MoyenDeTransportSurEau implements AUnIdentifiant {

    //rajouter les nationalite..faire fonction qui va lire toutes les nationalites au demarrage et rajouter uk voir liste de florent
    private String nom;
    private String pavillon;
    private String portAttache;
    private int tonnage;
    private float longeur;
    private Equipage equipage;

    public Bateau(String nom, String portAttache, int tonnage, float longeur, String pavillon) throws ShipWithoutIdentificationException {
        if (nom.isEmpty() || portAttache.isEmpty()) {
            throw new ShipWithoutIdentificationException("Nom et port d'attache requis");
        } else {
            this.nom = nom;
            this.portAttache = portAttache;
            this.tonnage = tonnage;
            this.longeur = longeur;
            this.pavillon=pavillon;
        }
    }

    @Override
    public String getIdentifiant() {
        return getNom() + getPortAttache();
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the portAttache
     */
    public String getPortAttache() {
        return portAttache;
    }

    /**
     * @param portAttache the portAttache to set
     */
    public void setPortAttache(String portAttache) {
        this.portAttache = portAttache;
    }

    /**
     * @return the tonnage
     */
    public int getTonnage() {
        return tonnage;
    }

    /**
     * @param tonnage the tonnage to set
     */
    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    /**
     * @return the longeur
     */
    public float getLongeur() {
        return longeur;
    }

    /**
     * @param longeur the longeur to set
     */
    public void setLongeur(float longeur) {
        this.longeur = longeur;
    }

    @Override
    public int getNombreHumains() {
        return equipage.getNombreHumains();
    }

    /**
     * @return the equipage
     */
    public Equipage getEquipage() {
        return equipage;
    }

    /**
     * @param equipage the equipage to set
     */
    public void setEquipage(Equipage equipage) {
        this.equipage = equipage;
    }

    @Override
    public String toString() {
        return getNom() + " / " + getPavillon();
    }

    /**
     * @return the pavillon
     */
    public String getPavillon() {
        return pavillon;
    }

    /**
     * @param pavillon the pavillon to set
     */
    public void setPavillon(String pavillon) {
        this.pavillon = pavillon;
    }

}
