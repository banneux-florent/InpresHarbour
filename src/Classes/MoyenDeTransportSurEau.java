package Classes;

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
public abstract class MoyenDeTransportSurEau extends MoyenDeTransport implements Serializable {

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
