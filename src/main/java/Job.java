package main.java;

import java.lang.System;
import java.lang.*;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Job implements Runnable {

    private Distribution server;
    private double serviceProbability;
    private double serviceTime;
    private SimulationSystem simulationSystem;
    private double timer;

    public Job(SimulationSystem simulationSystem) {
        this.simulationSystem = simulationSystem;
    }

    public Distribution getserver() {
        return server;
    }

    public void setserver(Distribution server) {
        this.server = server;
    }

    public double getServiceProbability() {
        return serviceProbability;
    }

    public void setServiceProbability(double serviceProbability) {
        this.serviceProbability = serviceProbability;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }


    @Override
    public void run() {
        double start=System.currentTimeMillis();
        try {
            this.serviceTime = server.calculateServiceTime(this.serviceProbability);
            this.server.addToProcessedJobs();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The service time for " + Thread.currentThread().getName() + " with server " + this.server.getClass().getSimpleName() + " is " + this.serviceTime);
        this.getserver().unlockServer(this);
        this.timer = System.currentTimeMillis()-start/1000.0;
    }

    public double getTimer(){
        return this.timer;
    }

}
