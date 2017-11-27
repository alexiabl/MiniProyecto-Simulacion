package main.java;

import com.sun.security.ntlm.Server;

import java.lang.System;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private ArrayList<Distribution> servers = new ArrayList<>();
    private long simulationTimer;
    private ServerController serverController;
    private int nSimulations;
    private Map<Job, Double> arrivals;
    private Map<Job,Double> started;
    private Map<Job, Double> finished;

    public Simulation(int nsimulations) {
        this.nSimulations = nsimulations;
        this.system = new SimulationSystem(nsimulations);
        this.simulationTimer = System.currentTimeMillis();
        this.started = new HashMap<>();
        this.finished = new HashMap<>();
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
        Random random = new Random();
        int numberJobs = 0;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(this.nSimulations);
        while (numberJobs <= this.nSimulations) {
            long elapsedTime = 0;
            int processedJobs = 0;
            while (elapsedTime <= 10 && processedJobs < this.nSimulations) {
                long startTimer = System.currentTimeMillis();
                long arrivalTime = (long)(30* Math.log(random.nextDouble()));
                double serviceProbability = random.nextDouble();
                if (!this.checkServersBusy()) {
                    long startJob = System.currentTimeMillis();
                    if (this.system.getWaitingQueue().size() != 0) {
                        Job job = this.system.dispatchFromQueue();
                        executorService.schedule(job, arrivalTime, TimeUnit.MILLISECONDS);
                    } else {
                        executorService.schedule(new Job(this.system, serviceProbability, this.serverController), arrivalTime, TimeUnit.MILLISECONDS);
                    }
                    long endjob = System.currentTimeMillis();
                    this.system.responseTime += endjob-startJob;
                } else {
                    Job job = new Job(this.system, serviceProbability, this.serverController);
                    System.out.println("All servers busy");
                    this.system.addToWaitingQueue(job);
                }
                processedJobs++;
                long end = System.currentTimeMillis();
                elapsedTime+= end-startTimer;

            }
            if (!executorService.isTerminated()){
                this.system.numberJobsRemainingAfter += this.system.waitingQueue.size();
            }
            numberJobs++;
        }
        this.system.waitingTimeForJob = this.serverController.getWaitJobTime();
        this.system.maxWaitTime = this.serverController.getMaxWaitTime();
        executorService.shutdownNow();
    }

    public void calculateSystemStats() {
        System.out.println("a- Exp waiting time for job = " + this.system.calculateExpWaitingTimeJob()+" seconds");
        System.out.println("b- Exp response time = " + this.system.calculateExpResponseTime()+" seconds");
        System.out.println("c- Exp queue length on arrival = " + this.system.calculateExpQueueLengthOnArrival());
        System.out.println("d- Max wait time = " + this.system.calculateMaxWaitTime()+" seconds");
        System.out.println("e- Max queue length = " + this.system.calculateMaxQueueLength());
        System.out.println("f- Probability one server available = " + this.system.calculateProbOneServerAvailable());
        System.out.println("g- Probability two servers available = " + this.system.calculateProbTwoServersAvailable());

        System.out.println("h- Exp number of jobs per server = " + this.system.calculateExpNumberJobsPerServer());
        System.out.println("i- Exp time each server idle= ");
        System.out.println("  - Gamma1 = "+this.system.calculateGamma1TimeIdle()+" minutes");
        System.out.println("  - Gamma2 = "+this.system.calculateGamma2TimeIdle()+" minutes");
        System.out.println("  - Exponential = "+this.system.calculateExponentialTimeIdle()+" minutes");
        System.out.println("  - Uniform = "+this.system.calculateUniformTimeIdle()+" minutes");
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
