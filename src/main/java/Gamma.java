package main.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Gamma implements Distribution {

    private int alpha;
    private int nu;
    private boolean isLocked;
    public int nJobs;

    public Gamma() {

    }

    public Gamma(int alpha, int nu) {
        this.alpha = alpha;
        this.nu = nu;
    }

    public int getalpha() {
        return alpha;
    }

    public void setalpha(int alpha) {
        this.alpha = alpha;
    }

    public int getNu() {
        return nu;
    }

    public void setNu(int nu) {
        this.nu = nu;
    }

    public int getnJobs(){
        return this.nJobs;
    }

    @Override
    public synchronized double calculateServiceTime(double probability) throws InterruptedException {
        this.isLocked = true;
        this.nJobs++;
        double serviceTime = 0.0;
            for (int i = 0; i <= alpha; i++) {
                serviceTime += (-1 / nu) * Math.log(1 - probability);
            }
        this.isLocked = false;
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        return this.isLocked;
    }
}
