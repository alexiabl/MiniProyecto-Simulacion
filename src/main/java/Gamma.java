package main.java;

import java.lang.*;
import java.lang.System;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Gamma implements Distribution {

    private int alpha;
    private double nu;
    private int numberJobsProcessed;
    private long timeIdle;
    public ReentrantLock lock;
    private long timer;



    public Gamma(int alpha, double nu) {
        this.alpha = alpha;
        this.nu = nu;
        this.numberJobsProcessed=0;
        lock = new ReentrantLock();
        this.timer=0;
    }

    public int getalpha() {
        return alpha;
    }

    public void setalpha(int alpha) {
        this.alpha = alpha;
    }

    public double getNu() {
        return nu;
    }

    public void setNu(int nu) {
        this.nu = nu;
    }


    @Override
    public double calculateServiceTime(double probability) throws InterruptedException {
        long temp = this.timer;
        this.timer+=System.currentTimeMillis();
        this.timeIdle += timer-temp;
            double serviceTime=0.0;
            for (int i = 0; i <= alpha; i++) {
                serviceTime += (-1 / nu) * Math.log(1 - probability);
            }
            this.addToProcessedJobs();
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        return this.lock.isLocked();
    }

    @Override
    public void unlockServer() {
        if (this.lock.isHeldByCurrentThread()) {
            this.lock.unlock();
        }
    }

    @Override
    public void lockServer(){
        this.lock.lock();
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
