package Classes;

import Exceptions.SailorIndicatedIsNotACaptainException;
import Exceptions.SailorIndicatedIsNotASecondException;
import Exceptions.ShipWithoutIdentificationException;
import java.io.Serializable;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BateauPlaisance extends Bateau implements Serializable {

    public static enum TypePermis {
        PlaisanceOptionCotiere,
        PlaisanceExtentionHauturiere
    }

    private TypePermis typePermis;

    public BateauPlaisance(String nom, String portAttache, int tonnage, float longeur, TypePermis typePermis, String pavillon) throws ShipWithoutIdentificationException {
        super(nom, portAttache, tonnage, longeur, pavillon);
        this.typePermis = typePermis;
    }

    public BateauPlaisance(BateauPlaisance bateauPlaisance) throws ShipWithoutIdentificationException, SailorIndicatedIsNotACaptainException, SailorIndicatedIsNotASecondException {
        super(bateauPlaisance.getNom(), bateauPlaisance.getPortAttache(), bateauPlaisance.getTonnage(), bateauPlaisance.getLongeur(), bateauPlaisance.getPavillon());
        this.setEquipage(new Equipage(bateauPlaisance.getEquipage()));
        this.typePermis = bateauPlaisance.getTypePermis();
    }

    public BateauPlaisance() {}

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
        return "BateauPlaisance / " + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
