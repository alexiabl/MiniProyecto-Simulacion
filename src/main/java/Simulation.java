package main.java;

import com.sun.security.ntlm.Server;

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
    private ServerController serverController;
    private int nSimulations;

    public Simulation(int nsimulations) {
        this.nSimulations = nsimulations;
        this.system = new SimulationSystem(nsimulations);
        this.simulationTimer = System.currentTimeMillis();
        this.started = new ArrayList<>();
        this.finished = new ArrayList<>();
        this.servers = new ArrayList<>();
        this.gamma1 = new Gamma(7, 3);
        this.gamma2 = new Gamma(5, 2);
        this.exponential = new Exponential(0.3);
        this.uniform = new Uniform(4, 9);
        system.setExponential(exponential);
        system.setGamma1(gamma1);
        system.setGamma2(gamma2);
        system.setUniform(uniform);
        this.serverController = new ServerController(gamma1, gamma2, exponential, uniform, system);
    }

    public void startSystem() throws InterruptedException {
        long arrivalTime = 0;
        Random random = new Random();
        double elapsedTime = 0;
        int numberJobs = 0;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(this.nSimulations);
        while (numberJobs <= this.nSimulations) { //36 segundos es el total de tiempo que esta abierto el sistema ya que en 10 horas hay 36,000 segundos
            //system.checkQueue();
            double arrivalProbability = Math.random();
            double serviceProbability = random.nextDouble();
            arrivalTime = (long)(arrivalTime - 30 * Math.log(arrivalProbability));
            long startJob = System.currentTimeMillis();
            if (!this.checkServersBusy()) {
                if (this.system.getWaitingQueue().size() != 0) {
                    Job job = this.system.dispatchFromQueue();
                    executorService.schedule(job, 2, TimeUnit.MILLISECONDS);
                } else {
                    executorService.schedule(new Job(this.system, serviceProbability, this.serverController), 2, TimeUnit.MILLISECONDS);
                }
            } else {
                Job job = new Job(this.system, serviceProbability, this.serverController);
                System.out.println("All servers busy");
                this.system.addToWaitingQueue(job);
            }
            long end = System.currentTimeMillis();
            elapsedTime += (end - startJob) / 1000.0;
            numberJobs++;
        }
        double totalWaitJobTime = this.serverController.getWaitJobTime();
        this.system.waitingTimeForJob = totalWaitJobTime;
        double maxWaitTime = this.serverController.getMaxWaitTime();
        this.system.waitTime = maxWaitTime;
        executorService.shutdownNow();
    }

    public double getElapsedTimeInSeconds() {
        return (System.currentTimeMillis() - this.simulationTimer) / 1000.0;
    }

    public void calculateSystemStats() {
        System.out.println("a- Exp waiting time for job = " + this.system.calculateExpWaitingTimeJob());
        System.out.println("b- Exp response time = " + this.system.calculateExpResponseTime());
        System.out.println("c- Exp queue length on arrival = " + this.system.calculateExpQueueLengthOnArrival());
        System.out.println("d- Max wait time = " + this.system.calculateMaxWaitTime());
        System.out.println("e- Max queue length = " + this.system.calculateMaxQueueLength());
        System.out.println("f- Probability one server available = " + this.system.calculateProbOneServerAvailable());
        System.out.println("g- Probability two servers available = " + this.system.calculateProbTwoServersAvailable());

        System.out.println("h- Exp number of jobs per server = " + this.system.calculateExpNumberJobsPerServer());
        System.out.println("i- Exp time each server idle= ");
        System.out.println("  - Gamma1 = "+this.system.calculateGamma1TimeIdle());
        System.out.println("  - Gamma2 = "+this.system.calculateGamma2TimeIdle());
        System.out.println("  - Exponential = "+this.system.calculateExponentialTimeIdle());
        System.out.println("  - Uniform = "+this.system.calculateUniformTimeIdle());
        System.out.println("j- Exp number jobs remaining = " + this.system.calculateExpNumberJobsRemainingAfter());
        System.out.println("k- Percentage jobs left early = " + this.system.calculatePercentageJobsLeftEarly());

    }

    public void runSimulation() throws InterruptedException {
        this.startSystem();
    }

    public boolean checkServersBusy() {
        boolean allBusy = false;
        if (gamma1.isLocked() && gamma2.isLocked() && uniform.isLocked() && exponential.isLocked()) {
            allBusy = true;
        }
        return allBusy;
    }

}
