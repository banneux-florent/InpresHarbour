package Classes;

import Exceptions.ShipWithoutIdentificationException;

/**
 *
 * @author Florent & Wadi
 */
public class BateauPeche extends Bateau {

    public static enum TypeDePeche {
        Thonier,
        Morutier,
        Crevettier
    }

    private TypeDePeche typeDePeche;

    public BateauPeche(String nom, String portAttache, int tonnage, float longeur, TypeDePeche typeDePeche, String pavillon) throws ShipWithoutIdentificationException {
        super(nom, portAttache, tonnage, longeur,pavillon);
        this.typeDePeche = typeDePeche;
    }

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
    
}
