package main.java;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class SimulationSystem {
    public double waitingTimeForJob;
    public double responseTime;
    public double queueLengthJobArrives;
    public double waitTime;
    public double queueLength;
    public double probOneServerAvailable;
    public double probTwoServersAvailable;
    public double timeServerIdle;
    public double numberJobsRemainingAfter;
    public double numberJobsLeftEarly;
    public double numberJobsByServer;
    public ArrayBlockingQueue<Job> waitingQueue;
    public int numberSimulations;
    public double maxQueueLength;
    private Gamma gamma1;
    private Gamma gamma2;
    private Exponential exponential;
    private Uniform uniform;

    public SimulationSystem(int numberSimulations) {
        waitingQueue = new ArrayBlockingQueue<Job>(1000);
        this.numberSimulations = numberSimulations;
    }

    public Gamma getGamma1() {
        return gamma1;
    }

    public void setGamma1(Gamma gamma1) {
        this.gamma1 = gamma1;
    }

    public Gamma getGamma2() {
        return gamma2;
    }

    public void setGamma2(Gamma gamma2) {
        this.gamma2 = gamma2;
    }

    public Exponential getExponential() {
        return exponential;
    }

    public void setExponential(Exponential exponential) {
        this.exponential = exponential;
    }

    public Uniform getUniform() {
        return uniform;
    }

    public void setUniform(Uniform uniform) {
        this.uniform = uniform;
    }


    public double calculateExpWaitingTimeJob() {
        return this.waitingTimeForJob/numberSimulations;
    }

    public double calculateExpResponseTime() {
        return this.responseTime/numberSimulations;
    }

    public double calculateExpQueueLengthOnArrival() {
        return this.queueLength/numberSimulations;
    }

    public double calculateMaxWaitTime() {
        return this.waitTime/numberSimulations;
    }

    public double calculateMaxQueueLength() {
        return this.maxQueueLength;
    }

    public double calculateExpNumberJobsPerServer() {
        double number = gamma1.getNumberProcessedJobs()+gamma2.getNumberProcessedJobs()+exponential.getNumberProcessedJobs()+uniform.getNumberProcessedJobs();
        return number/numberSimulations;
    }

    public double calculateExpNumberJobsRemainingAfter() {
        return 0;
    }

    public double calculatePercentageJobsLeftEarly() {
        return 0;

    }

    public double calculateProbOneServerAvailable() {
        return 0;
    }

    public double calculateProbTwoServersAvailable() {
        return 0;
    }

    public void addToWaitingQueue(Job job) throws InterruptedException {
        this.waitingQueue.add(job);
    }

    public Job dispatchFromQueue() {
        Job job = this.waitingQueue.remove();
        return job;
    }

    public Queue<Job> getWaitingQueue(){
        return this.waitingQueue;
    }
}
