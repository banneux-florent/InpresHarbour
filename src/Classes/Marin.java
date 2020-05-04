/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDate;
import Exceptions.SailorWithoutIdentificationException;
import java.util.Objects;

/**
 *
 * @author flore
 */
public class Marin extends Humain implements AUnIdentifiant {
    
    public static enum Fonction {
        Capitaine,
        Second,
        Bosco,
        MaitreMecanicien,
        Matelot,
        Passager
    }
    
    private Fonction fonction;

    /**
     * Constructeur de d'initialisation
     *
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param fonction
     */
    public Marin(String nom, String prenom, LocalDate dateNaissance, Fonction fonction) throws SailorWithoutIdentificationException {
        super(nom, prenom, dateNaissance);
        if (nom.length() == 0 || prenom.length() == 0) {
            throw new SailorWithoutIdentificationException("Le nom et le pr�nom ne peuvent pas �tre vides.");
        } else {
            this.fonction = fonction;
        }
    }
    
    public Marin(String nom, String prenom, LocalDate dateNaissance) throws SailorWithoutIdentificationException {
        super(nom, prenom, dateNaissance);
        if (nom.length() == 0 || prenom.length() == 0) {
            throw new SailorWithoutIdentificationException("Le nom et le pr�nom ne peuvent pas �tre vides.");
        }
    }
    
    /**
     * Constructeur de copie
     *
     * @param marin
     */
    public Marin(Marin marin) {
        super(marin.getNom(), marin.getPrenom(), marin.getDateNaissance());
        this.fonction = marin.getFonction();
    }

    @Override
    public String getIdentifiant() {
        return getNom() + getPrenom() + getDateNaissanceString();
    }

    /**
     * @return the fonction
     */
    public Fonction getFonction() {
        return fonction;
    }

    /**
     * @param fonction the fonction to set
     */
    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return "[" + getFonction() + "] " + getNom() + " " + getPrenom() + " (" + getDateNaissanceString() +  ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Marin other = (Marin) obj;
        return (this.getNom().equals(other.getNom()) && this.getPrenom().equals(other.getPrenom()) && this.getDateNaissanceString().equals(other.getDateNaissanceString()));
    }
    
    
    
}