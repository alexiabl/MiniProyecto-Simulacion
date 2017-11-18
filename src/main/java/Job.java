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
    private main.java.System system;

    public Job(main.java.System system) {
        this.system = system;
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
        try {
            this.serviceTime = server.calculateServiceTime(this.serviceProbability);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The service time for " + Thread.currentThread().getName() + " with server " + this.server.getClass().getSimpleName() + " is " + this.serviceTime);
    }

}
