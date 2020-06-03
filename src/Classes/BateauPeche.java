package Classes;

import Exceptions.ShipWithoutIdentificationException;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BateauPeche extends Bateau implements Serializable{

    public static enum TypeDePeche {
        Thonier,
        Morutier,
        Crevettier
    }

    private TypeDePeche typeDePeche;

    public BateauPeche(String nom, String portAttache, int tonnage, float longeur, TypeDePeche typeDePeche, String pavillon) throws ShipWithoutIdentificationException {
        super(nom, portAttache, tonnage, longeur, pavillon);
        this.typeDePeche = typeDePeche;
    }

    public BateauPeche(BateauPeche bateauPeche) throws ShipWithoutIdentificationException {
        super(bateauPeche.getNom(), bateauPeche.getPortAttache(), bateauPeche.getTonnage(), bateauPeche.getLongeur(), bateauPeche.getPavillon());
        this.typeDePeche = bateauPeche.getTypeDePeche();
    }
    
    public BateauPeche() {}

    /**
     * @return the typeDePeche
     */
    public TypeDePeche getTypeDePeche() {
        return typeDePeche;
    }

    /**
     * @param typeDePeche the typeDePeche to set
     */
    public void setTypeDePeche(TypeDePeche typeDePeche) {
        this.typeDePeche = typeDePeche;
    }

    @Override
    public String toString() {
        return "BateauPeche / " +super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
}
