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
    public ReentrantLock lock;



    public Uniform(int a, int b) {
        this.a = a;
        this.b = b;
        this.jobs = new ArrayList<>(1);
        this.numberJobsProcessed=0;
        lock = new ReentrantLock();
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
        double serviceTime=0.0;
        double temp = this.timeIdle;
        this.timeIdle += System.currentTimeMillis() - temp / 1000.0;
        serviceTime = a + (b - a) * probability;
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
    public double getIdle() {
        return this.timeIdle;
    }

    @Override
    public void lockServer(){
        this.lock.lock();
    }

}
