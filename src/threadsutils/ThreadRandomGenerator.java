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
    private boolean showMessages = true;
    private final int lowerBound;
    private final int upperBound;
    private final int waitingTime;
    
    public ThreadRandomGenerator(IUserNumber userNumber, boolean showMessages, int lowerBound, int upperBound, int waitingTime) {
        this.userNumber = userNumber; 
        this.showMessages = showMessages;
        this.lowerBound = lowerBound; 
        this.upperBound = upperBound;
        this.waitingTime = waitingTime;
    }
    
    @Override
    public void run() {
        int generatedNumber;
        while (true) {
            generatedNumber = (int) (this.lowerBound + Math.random()*(this.upperBound - this.lowerBound));
            if (this.showMessages)
                System.out.println("[ThreadRandomGenerator | Info] Bean " + this.userNumber.getId() + " generated " + generatedNumber);
            this.userNumber.processNumber(generatedNumber);
            try {
                Thread.sleep(this.waitingTime);
            } catch (InterruptedException e) {
                System.err.println("[ThreadRandomGenerator | Error] " + e.getMessage());
            }
        }
    }
    
}
