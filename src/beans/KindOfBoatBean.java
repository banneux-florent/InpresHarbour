/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import threadsutils.IUtilisateurNombre;

/**
 *
 * @author Florent & Wadi
 */
public class KindOfBoatBean implements IUtilisateurNombre {
    
    private String identifiant;
    
    public KindOfBoatBean(String identifiant) {
        this.identifiant = identifiant;
    }
    
    public String getIdentifiant() {
        return this.identifiant;
    }
    
    public void traiteNombre(int n){
        
    }
    
}
