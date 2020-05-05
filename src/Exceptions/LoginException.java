package Exceptions;

import Classes.DialogErreur;

/**
 *
 * @author Florent & Wadi
 */
public class LoginException extends Exception {

    public LoginException(String titreErreur, String messageErreur) {
        super(messageErreur);
        DialogErreur dialogErreur = new DialogErreur(titreErreur, messageErreur);
        dialogErreur.setVisible(true);
    }

}
