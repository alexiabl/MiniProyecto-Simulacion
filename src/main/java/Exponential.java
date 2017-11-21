package main.java;

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
    public synchronized double calculateServiceTime(double probability) throws InterruptedException {
        this.isLocked = true;
        double serviceTime = (-1 / rate) * Math.log(1 - probability);
        this.isLocked = false;
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        this.isLocked = false;
        if (this.jobs.size()>0){
            this.isLocked=true;
        }
        return this.isLocked;
    }

    @Override
    public void unlockServer(Job job) {
        this.jobs.remove(job);
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
