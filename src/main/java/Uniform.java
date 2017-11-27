package main.java;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Uniform implements Distribution {

    private int a;
    private int b;
    private int numberJobsProcessed;
    private long timeIdle;
    public ReentrantLock lock;
    private long timer;



    public Uniform(int a, int b) {
        this.a = a;
        this.b = b;
        this.numberJobsProcessed=0;
        lock = new ReentrantLock();
        this.timer=0;
    }


    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public double calculateServiceTime(double probability) throws InterruptedException {
        long temp = this.timer;
        this.timer+=System.currentTimeMillis();
        this.timeIdle += timer-temp;
        double serviceTime = a + (b - a) * probability;
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

    @Override
    public void lockServer(){
        this.lock.lock();
    }

}
