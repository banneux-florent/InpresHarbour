package Exceptions;

import Classes.DialogErreur;

/**
 *
 * @author Florent & Wadi
 */
public class CapitainerieException extends Exception {

    public CapitainerieException(String titreErreur, String messageErreur) {
        super(messageErreur);
        DialogErreur dialogErreur = new DialogErreur(titreErreur, messageErreur);
        dialogErreur.setVisible(true);
    }
    
    public CapitainerieException(String titreErreur, String messageErreur, boolean modal) {
        super(messageErreur);
        DialogErreur dialogErreur = new DialogErreur(titreErreur, messageErreur, modal);
        dialogErreur.setVisible(true);
    }

}
