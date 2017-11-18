package main.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Exponential implements Distribution{
    private boolean isLocked;

    private double rate;

    public Exponential() {

    }

    public Exponential(double nu) {
        this.rate = nu;
    }

    public synchronized double getNu() {
        return rate;
    }

    public synchronized void setNu(double nu) {
        this.rate = nu;
    }

    @Override
    public synchronized double calculateServiceTime(double probability) throws InterruptedException {
        this.isLocked = true;
        double serviceTime = (-1 / rate) * Math.log(1 - probability);
        this.isLocked = false;
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        return this.isLocked;
    }

}
