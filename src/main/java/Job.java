package main.java;

import java.lang.System;
import java.lang.*;
import java.util.Random;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Job implements Runnable {

    private double serviceProbability;
    private double serviceTime;
    private SimulationSystem simulationSystem;
    private long timer;
    private ServerController serverController;
    private long waitJobTime;
    private int id;

    public Job(SimulationSystem simulationSystem, double serviceProbability, ServerController serverController){
        this.simulationSystem = simulationSystem;
        this.serviceProbability = serviceProbability;
        this.serverController = serverController;
        this.waitJobTime=0;
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

    public SimulationSystem updateSystem(){
        return this.simulationSystem;
    }


    @Override
    public void run() {
        long start=System.currentTimeMillis();
        Distribution server = null;
        try {
            server= this.serverController.assignServer();
            this.waitJobTime = System.currentTimeMillis()-start;
            server.lockServer();
            this.serviceTime = server.calculateServiceTime(this.serviceProbability);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*        try {
            Thread.currentThread().sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("The service time for Job" +" with server " + server.getClass().getSimpleName() + " is " + this.serviceTime);
        this.timer = System.currentTimeMillis()-start;
        if (server!=null) {
            server.unlockServer();
        }
    }


    public long getTimer(){
        return this.timer;
    }

    public long getWaitJobTime(){
        return this.waitJobTime;
    }

}
