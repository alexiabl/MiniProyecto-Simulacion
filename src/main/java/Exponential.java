package main.java;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Exponential implements Distribution{

    private double rate;
    private int numberJobsProcessed;
    private long timeIdle;
    public ReentrantLock lock;
    private long timer;


    public Exponential(double nu) {
        this.rate = nu;
        this.numberJobsProcessed =0;
        lock = new ReentrantLock();
        this.timer=0;
    }

    public  double getNu() {
        return rate;
    }

    public  void setNu(double nu) {
        this.rate = nu;
    }

    @Override
    public double calculateServiceTime(double probability) throws InterruptedException {
        long temp = this.timer;
        this.timer+=System.currentTimeMillis();
        this.timeIdle += timer-temp;
        double serviceTime = (-1 / rate) * Math.log(1 - probability);
        this.addToProcessedJobs();
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        return this.lock.isLocked();

    }

    @Override
    public void lockServer(){
        this.lock.lock();
    }

    @Override
    public void unlockServer() {
        if (this.lock.isHeldByCurrentThread()) {
            this.lock.unlock();
        }
    }

    @Override
    public void addToProcessedJobs() {
        this.numberJobsProcessed++;
    }

    @Override
    public int getNumberProcessedJobs() {
        return this.numberJobsProcessed;
    }

    @Override
    public long getIdle() {
        return this.timeIdle;
    }

}
