/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsutils;

/**
 *
 * @author flore
 */
public class ThreadRandomGenerator extends Thread {
    
    private IUtilisateurNombre utilisateurThread;
    private int borneInferieure;
    private int borneSuperieure;
    private int multipleDeclenchement;
    private int tempsPause;
    private int nombreProduit = -1;
    
    public ThreadRandomGenerator(IUtilisateurNombre utilisateurThread, int borneInferieure, int borneSuperieure, int multipleDeclenchement, int tempsPause) {
        this.utilisateurThread = utilisateurThread; 
        this.borneInferieure = borneInferieure; 
        this.borneSuperieure = borneSuperieure;
        this.multipleDeclenchement = multipleDeclenchement;
        this.tempsPause = tempsPause;
    }
    
    public void run() {
        Double dr;
        while (true) {
            dr = this.borneInferieure + Math.random()*(this.borneSuperieure - this.borneInferieure);
            this.nombreProduit = dr.intValue();
            System.out.println(this.utilisateurThread.getIdentifiant() +"> nombreProduit = " + this.nombreProduit);
            if (this.nombreProduit % this.multipleDeclenchement == 0) {
                System.out.println(this.utilisateurThread.getIdentifiant() + "> -------------- !!!!!!! " + this.nombreProduit + "!!!!");
                this.utilisateurThread.traiteNombre(this.nombreProduit);
            }
            try {
                Thread.sleep(this.tempsPause*1000);
            } catch (InterruptedException e) {
                System.out.println("Erreur de thread interrompu : " + e.getMessage());
            }
        }
    }
    
}
