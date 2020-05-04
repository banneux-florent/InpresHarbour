    package Exceptions;

import Classes.DialogErreur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author flore
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
