package Exceptions;

import Classes.DialogErreur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author WADI
 */
public class LoginException extends Exception {

    public LoginException(String titreErreur, String messageErreur) {
        super(messageErreur);
        DialogErreur dialogErreur = new DialogErreur(titreErreur, messageErreur);
        dialogErreur.setVisible(true);
    }

}
