package main.java;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Exponential implements Distribution{

    private boolean isLocked;
    private double rate;
    private ArrayList<Job> jobs;
    private int numberJobsProcessed;
    private double timeIdle;
    private ReentrantLock lock = new ReentrantLock();


    public Exponential(double nu) {
        this.rate = nu;
        this.jobs = new ArrayList<>(1);
        this.numberJobsProcessed =0;
    }

    public synchronized double getNu() {
        return rate;
    }

    public synchronized void setNu(double nu) {
        this.rate = nu;
    }

    @Override
    public double calculateServiceTime(double probability) throws InterruptedException {
        double serviceTime = (-1 / rate) * Math.log(1 - probability);
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
        this.lock.unlock();
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
    public double getIdle() {
        return this.timeIdle;
    }

}
