package main.java;

import java.lang.System;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Simulation {

    private main.java.System system;
    private Gamma gamma1;
    private Gamma gamma2;
    private Exponential exponential;
    private Uniform uniform;

    public Simulation() {
        this.gamma1 = new Gamma(7, 3);
        this.gamma2 = new Gamma(5, 2);
        this.exponential = new Exponential(0.3);
        this.uniform = new Uniform(4, 9);
    }

    public void runSimulation(int nSimulations, double arrivalRate) throws InterruptedException, ExecutionException {
        system = new main.java.System();
        Random random = new Random();
        ExecutorService executorService = Executors.newFixedThreadPool(nSimulations);
        for (int i = 0; i < nSimulations; i++) {
            Job job = new Job(system);
            double serviceProbability = random.nextDouble();
            job.setServiceProbability(serviceProbability);
            Job job1 = assignServer(job);
            executorService.execute(job1);
        }
    }

    public void test() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1000, new CustomThreadFactory("Simulation"));
        scheduledExecutorService.schedule(new Job(this.system), 2, TimeUnit.SECONDS);
    }

    public Job assignServer(Job job) throws InterruptedException {
        Random random = new Random();
        long start = System.nanoTime();
        int chooseServer = random.nextInt(4) + 1;
        switch (chooseServer) {
            case 1:
                if (gamma1.isLocked() || start - System.nanoTime() < 6) {
                    system.addToWaitingQueue(job);
                } else {
                    if (system.getWaitingQueue().size()>0){
                        job = system.dispatchFromQueue();
                        job.setserver(gamma1);                    }
                    else {
                        job.setserver(gamma1);                    }
                }
                break;
            case 2:
                if (gamma1.isLocked() || start - System.nanoTime() < 6) {
                    system.addToWaitingQueue(job);
                } else {
                    if (system.getWaitingQueue().size()>0){
                        job = system.dispatchFromQueue();
                        job.setserver(gamma2);
                    }
                    else {
                        job.setserver(gamma2);
                    }

                }
                break;
            case 3:
                if (exponential.isLocked() || start - System.nanoTime() < 6) {
                    system.addToWaitingQueue(job);
                } else {
                    if (system.getWaitingQueue().size()>0){
                        job = system.dispatchFromQueue();
                        job.setserver(exponential);
                    }
                    else {
                        job.setserver(exponential);
                    }
                }
                break;
            case 4:
                if (uniform.isLocked() || start - System.nanoTime() < 6) {
                    system.addToWaitingQueue(job);
                } else {
                    if (system.getWaitingQueue().size()>0){
                        job = system.dispatchFromQueue();
                        job.setserver(uniform);                    }
                    else {
                        job.setserver(uniform);
                    }

                }
                break;
        }
        return job;
    }


    public double getExponentialArrivalRate() throws InterruptedException {
        double arrivalProbability = Math.random();
        Exponential exponentialArrival = new Exponential(30);
        double interArrivalTime = exponentialArrival.calculateServiceTime(arrivalProbability);
        return interArrivalTime;
    }

}
