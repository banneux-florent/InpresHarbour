package Classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Humain implements Serializable {
    
    private String nom;
    private String prenom;
    
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate dateNaissance;

    /**
     * Constructeur d'initialisation
     *
     * @param nom
     * @param prenom
     * @param dateNaissance
     */
    public Humain(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public Humain() {}
    
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
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the dateNaissance
     */
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    /**
     * @return the dateNaissance
     */
    public String getDateNaissanceString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
        return dateNaissance.format(formatter);
    }

    /**
     * @param dateNaissance the dateNaissance to set
     */
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
}
