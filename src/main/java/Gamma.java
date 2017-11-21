package main.java;

import java.lang.*;
import java.lang.System;
import java.util.ArrayList;
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
    private ArrayList<Job> jobs;
    private int numberJobsProcessed;
    private double timeIdle;


    public Gamma(int alpha, int nu) {
        this.alpha = alpha;
        this.nu = nu;
        this.jobs = new ArrayList<>(1);
        this.numberJobsProcessed=0;
        this.timeIdle = java.lang.System.currentTimeMillis();
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

    public ArrayList getJobs(){
        return this.jobs;
    }


    @Override
    public synchronized double calculateServiceTime(double probability) throws InterruptedException {
        double temp = this.timeIdle;
        this.timeIdle = System.currentTimeMillis()-temp/1000.0;
        this.nJobs++;
        double serviceTime = 0.0;
            for (int i = 0; i <= alpha; i++) {
                serviceTime += (-1 / nu) * Math.log(1 - probability);
            }
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        this.isLocked=false;
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
