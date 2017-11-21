package main.java;

import java.lang.System;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Simulation {

    private SimulationSystem system;
    private Gamma gamma1;
    private Gamma gamma2;
    private Exponential exponential;
    private Uniform uniform;
    private ArrayList<Job> started;
    private ArrayList<Job> finished;
    private ArrayList<Distribution> servers = new ArrayList<>();
    private long simulationTimer;

    public Simulation(int nsimulations) {
        this.system = new SimulationSystem(nsimulations);
        this.simulationTimer = System.currentTimeMillis();
        this.started = new ArrayList<>();
        this.finished = new ArrayList<>();
        this.servers = new ArrayList<>();
        this.gamma1 = new Gamma(7, 3);
        this.gamma2 = new Gamma(5, 2);
        this.exponential = new Exponential(0.3);
        this.uniform = new Uniform(4, 9);
        servers.add(gamma1);
        servers.add(gamma2);
        servers.add(exponential);
        servers.add(uniform);
        system.setExponential(exponential);
        system.setGamma1(gamma1);
        system.setGamma2(gamma2);
        system.setUniform(uniform);
    }

    public void startSystem() throws InterruptedException {
        int arrivalTime = 0;
        Random random = new Random();
        double elapsedTime = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        while (elapsedTime < 36){ //36 segundos es el total de tiempo que esta abierto el sistema ya que en 10 horas hay 36,000 segundos
            long startJob = System.currentTimeMillis();
            double arrivalProbability = Math.random();
            double serviceProbability = random.nextDouble();
            arrivalTime = (int)(arrivalTime - 30*Math.log(arrivalProbability));
            Job job = new Job(system);
            job.setServiceProbability(serviceProbability);
            this.assignServer(job);
            if (job.getserver() != null) {
                executorService.execute(job);
            }
            long end = System.currentTimeMillis();
            elapsedTime += (end - startJob)/1000.0;
        }
        executorService.shutdown();
    }

    public void assignServer(Job job) throws InterruptedException {
        double waitingTime = 0;
        if (!checkServersBusy()){
            Random random = new Random();
            int chooseServer = random.nextInt(4) + 1;
            switch (chooseServer){
                case 1:
                    if (!gamma1.isLocked()){
                        job.setserver(gamma1);
                        waitingTime+=System.currentTimeMillis()/1000.0;
                        break;
                    }
                    else if (gamma1.isLocked() && this.getElapsedTimeInSeconds() < 6){
                        while (chooseServer == 1) {
                            chooseServer = random.nextInt(4) + 1;
                        }
                    }
                    else if (this.getElapsedTimeInSeconds() > 6){
                        system.numberJobsLeftEarly++;
                        job.notifyAll();
                    }

                case 2:
                    if (!gamma2.isLocked()){
                        job.setserver(gamma2);
                        waitingTime+=System.currentTimeMillis()/1000.0;
                        break;
                    }
                    else if (gamma2.isLocked() && this.getElapsedTimeInSeconds() < 6){
                        while (chooseServer == 2) {
                            chooseServer = random.nextInt(4) + 1;
                        }
                    }
                    else if (this.getElapsedTimeInSeconds() > 6){
                        system.numberJobsLeftEarly++;
                        job.notifyAll();
                    }
                case 3:
                    if (!exponential.isLocked()){
                        job.setserver(exponential);
                        waitingTime+=System.currentTimeMillis()/1000.0;
                        break;
                    }
                    else if (exponential.isLocked() && this.getElapsedTimeInSeconds()<6){
                        while (chooseServer == 3) {
                            chooseServer = random.nextInt(4) + 1;
                        }
                    }
                    else if (this.getElapsedTimeInSeconds() > 6){
                        system.numberJobsLeftEarly++;
                        job.notifyAll();
                    }
                case 4:
                    if (!uniform.isLocked()){
                        job.setserver(uniform);
                        waitingTime+=System.currentTimeMillis()/1000.0;
                        break;
                    }
                    else if (uniform.isLocked() && this.getElapsedTimeInSeconds() <6){
                        while (chooseServer ==4){
                            chooseServer = random.nextInt(4) + 1;
                        }
                    }
                    else if (this.getElapsedTimeInSeconds() > 6){
                        system.numberJobsLeftEarly++;
                        job.notifyAll();
                    }
            }
        }
        else{
            this.system.queueLengthJobArrives=this.system.waitingQueue.size();
            if (this.system.queueLengthJobArrives > this.system.maxQueueLength){
                this.system.maxQueueLength = this.system.queueLengthJobArrives;
            }
            this.system.queueLength +=1;
            this.system.addToWaitingQueue(job);
        }
        this.system.waitingTimeForJob+= waitingTime;
    }

    public double getElapsedTimeInSeconds(){
        return (System.currentTimeMillis() - this.simulationTimer) / 1000.0;
    }

    public void calculateSystemStats(){
        System.out.println("1- "+this.system.calculateExpNumberJobsPerServer());
        System.out.println("2- "+this.system.calculateExpNumberJobsRemainingAfter());
        System.out.println("3- "+this.system.calculateExpQueueLengthOnArrival());
        System.out.println("4- "+this.system.calculateExpResponseTime());
        System.out.println("5- "+this.system.calculateExpWaitingTimeJob());
        System.out.println("6- "+this.system.calculateMaxQueueLength());
        System.out.println("7- "+this.system.calculateMaxWaitTime());
        System.out.println("8- "+this.system.calculatePercentageJobsLeftEarly());
        System.out.println("9- "+this.system.calculateProbOneServerAvailable());
        System.out.println("10- "+this.system.calculateProbTwoServersAvailable());
    }

    public void runSimulation() throws InterruptedException {
        this.startSystem();
        this.calculateSystemStats();
    }

    public boolean checkServersBusy(){
        boolean allBusy = false;
        if (gamma1.isLocked() && gamma2.isLocked() && uniform.isLocked() && exponential.isLocked()){
            allBusy = true;
        }
        return allBusy;
    }

}
