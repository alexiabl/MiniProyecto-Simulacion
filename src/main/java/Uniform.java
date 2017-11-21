package main.java;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Uniform implements Distribution {

    private boolean isLocked;
    private int a;
    private int b;
    private ArrayList<Job> jobs;
    private int numberJobsProcessed;
    private double timeIdle;


    public Uniform(int a, int b) {
        this.a = a;
        this.b = b;
        this.jobs = new ArrayList<>(1);
        this.numberJobsProcessed=0;
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
    public synchronized double calculateServiceTime(double probability) throws InterruptedException {
        this.isLocked = true;
        double serviceTime = a + (b - a) * probability;
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
