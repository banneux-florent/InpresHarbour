/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Capitainerie.Capitainerie;
import java.util.LinkedList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Florent & Wadi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CapitainerieSavable {
    
    private LinkedList<Bateau> bateauAttenteEntrer = new LinkedList<Bateau>();
    private LinkedList<Bateau> bateauEnCoursDAmarrage = new LinkedList<Bateau>();
    private LinkedList<Bateau> bateauEntresDansLaRade = new LinkedList<Bateau>();

    private LinkedList<Ponton> pontons = new LinkedList<Ponton>();
    private LinkedList<Quai> quais = new LinkedList<Quai>();
    
    public CapitainerieSavable(Capitainerie capitainerie) {
        this.bateauAttenteEntrer = capitainerie.bateauAttenteEntrer;
        this.bateauEnCoursDAmarrage = capitainerie.bateauEnCoursDAmarrage;
        this.bateauEntresDansLaRade = capitainerie.bateauEntresDansLaRade;
        
        this.pontons = capitainerie.pontons;
        this.quais = capitainerie.quais;
    }
    
    public CapitainerieSavable() {}

    public LinkedList<Bateau> getBateauAttenteEntrer() {
        return bateauAttenteEntrer;
    }

    public void setBateauAttenteEntrer(LinkedList<Bateau> bateauAttenteEntrer) {
        this.bateauAttenteEntrer = bateauAttenteEntrer;
    }

    public LinkedList<Bateau> getBateauEnCoursDAmarrage() {
        return bateauEnCoursDAmarrage;
    }

    public void setBateauEnCoursDAmarrage(LinkedList<Bateau> bateauEnCoursDAmarrage) {
        this.bateauEnCoursDAmarrage = bateauEnCoursDAmarrage;
    }

    public LinkedList<Bateau> getBateauEntresDansLaRade() {
        return bateauEntresDansLaRade;
    }

    public void setBateauEntresDansLaRade(LinkedList<Bateau> bateauEntresDansLaRade) {
        this.bateauEntresDansLaRade = bateauEntresDansLaRade;
    }

    public LinkedList<Ponton> getPontons() {
        return pontons;
    }

    public void setPontons(LinkedList<Ponton> pontons) {
        this.pontons = pontons;
    }

    public LinkedList<Quai> getQuais() {
        return quais;
    }

    public void setQuais(LinkedList<Quai> quais) {
        this.quais = quais;
    }
    
}
