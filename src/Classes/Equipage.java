package Classes;

import Exceptions.SailorIndicatedIsNotACaptainException;
import Exceptions.SailorIndicatedIsNotASecondException;
import java.util.LinkedList;

/**
 *
 * @author Florent & Wadi
 */
public class Equipage {
    
    private Marin capitaine;
    private Marin second;
    
    private LinkedList<Marin> marins = new LinkedList<Marin>();

    /**
     * Consutructeur d'initialisation
     *
     * @param capitaine
     * @param second
     * @param marins
     */
    public Equipage(Marin capitaine, Marin second, LinkedList<Marin> marins) throws SailorIndicatedIsNotACaptainException, SailorIndicatedIsNotASecondException {
        if (capitaine.getFonction() != Marin.Fonction.Capitaine) {
            throw new SailorIndicatedIsNotACaptainException("Le marin indiqué n'est pas un capitaine.");
        } else {
            this.capitaine = capitaine;
        }
        if (second.getFonction() != Marin.Fonction.Second) {
            throw new SailorIndicatedIsNotASecondException("Le marin indiqué n'est pas un second.");
        } else {
            this.second = second;
        }
        
        for (int i = 0; i < marins.size(); i++) {
            this.marins.add(new Marin(marins.get(i)));
        }
    }
    
    /**
     * Consutructeur d'initialisation bis
     *
     * @param capitaine
     * @param second
     */
    public Equipage(Marin capitaine, Marin second) throws SailorIndicatedIsNotACaptainException, SailorIndicatedIsNotASecondException {
        this(capitaine, second, new LinkedList<Marin>());
    }

    /**
     * @return the capitaine
     */
    public Marin getCapitaine() {
        return capitaine;
    }

    /**
     * @param capitaine the capitaine to set
     */
    public void setCapitaine(Marin capitaine) {
        this.capitaine = capitaine;
    }

    /**
     * @return the second
     */
    public Marin getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(Marin second) {
        this.second = second;
    }

    /**
     * @return the marins
     */
    public LinkedList<Marin> getMarins() {
        return marins;
    }

    /**
     * @param marins the marins to set
     */
    public void setMarins(LinkedList<Marin> marins) {
        this.marins = marins;
    }
    
    public int getNombreHumains() {
        int nombreHumains = 0;
        if (capitaine != null) {
            nombreHumains++;
        }
        if (second != null) {
            nombreHumains++;
        }
        nombreHumains += marins.size();
        return nombreHumains;
    }
    
}
