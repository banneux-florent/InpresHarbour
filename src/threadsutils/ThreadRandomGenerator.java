/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsutils;

import beans.IUserNumber;

/**
 *
 * @author flore
 */
public class ThreadRandomGenerator extends Thread {
    
    private IUserNumber userNumber;
    private int lowerBound;
    private int upperBound;
    private int triggerMultiple;
    private int waitingTime;
    
    public ThreadRandomGenerator(IUserNumber userNumber, int lowerBound, int upperBound, int triggerMultiple, int waitingTime) {
        this.userNumber = userNumber; 
        this.lowerBound = lowerBound; 
        this.upperBound = upperBound;
        this.triggerMultiple = triggerMultiple;
        this.waitingTime = waitingTime;
    }
    
    public void run() {
        int generatedNumber;
        while (true) {
            generatedNumber = (int) (this.lowerBound + Math.random()*(this.upperBound - this.lowerBound));
            System.out.println(this.userNumber.getId() +" generated " + generatedNumber + ".");
            if (generatedNumber % this.triggerMultiple == 0) {
                System.out.println(this.userNumber.getId() + " is interested by " + generatedNumber + ".");
                this.userNumber.processNumber(generatedNumber);
            }
            try {
                Thread.sleep(this.waitingTime * 1000);
            } catch (InterruptedException e) {
                System.err.println("[ThreadRandomGenerator | Error] " + e.getMessage());
            }
        }
    }
    
}
