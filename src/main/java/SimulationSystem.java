package main.java;

import java.sql.Time;
import java.time.Duration;
import java.util.AbstractQueue;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class SimulationSystem {
    public long waitingTimeForJob;
    public long responseTime;
    public double queueLengthJobArrives;
    public long maxWaitTime;
    public double queueLength;
    public double probOneServerAvailable;
    public double probTwoServersAvailable;
    public double numberJobsRemainingAfter;
    public double numberJobsLeftEarly;
    public Queue<Job> waitingQueue;
    public int numberSimulations;
    public double maxQueueLength;
    private Gamma gamma1;
    private Gamma gamma2;
    private Exponential exponential;
    private Uniform uniform;

    public SimulationSystem(int numberSimulations) {
        waitingQueue = new ArrayBlockingQueue<Job>(numberSimulations+1);
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


    public long calculateExpWaitingTimeJob() {
        long expected = ((this.waitingTimeForJob/1000));
        return expected;
    }

    public long calculateExpResponseTime() {
        long expected = ((this.responseTime/1000));
        return expected;

    }

    public double calculateExpQueueLengthOnArrival() {
        return this.queueLengthJobArrives/numberSimulations;
    }

    public long calculateMaxWaitTime() {
        return this.maxWaitTime;
    }

    public double calculateMaxQueueLength() {
        return this.maxQueueLength;
    }

    public double calculateExpNumberJobsPerServer() {
        double number = gamma1.getNumberProcessedJobs()+gamma2.getNumberProcessedJobs()+exponential.getNumberProcessedJobs()+uniform.getNumberProcessedJobs();
        return number/numberSimulations;
    }

    public double calculateExpNumberJobsRemainingAfter() {
        return this.numberJobsRemainingAfter/numberSimulations;
    }

    public double calculatePercentageJobsLeftEarly() {
        return (this.numberJobsLeftEarly/100)*10;

    }

    public double calculateProbOneServerAvailable() {
        return 0;
    }

    public double calculateProbTwoServersAvailable() {
        return 0;
    }

    public long calculateGamma1TimeIdle(){
        long gammaidle = this.gamma1.getIdle();
        long result = (gammaidle/numberSimulations);
        result %= (1000*60);
        return result;
    }

    public long calculateGamma2TimeIdle(){
        long gammaidle = this.gamma2.getIdle();
        long result = (gammaidle/numberSimulations);
        result %= (1000*60);
        return result;
    }

    public long calculateExponentialTimeIdle(){
        long exponentialIdle = this.exponential.getIdle();
        long result = (exponentialIdle/numberSimulations);
        result %= (1000*60);
        return result;    }

    public long calculateUniformTimeIdle(){
        long uniformIdle = this.uniform.getIdle();
        long result = (uniformIdle/numberSimulations);
        result %= (1000*60);
        return result;
    }

    public void addToWaitingQueue(Job job) throws InterruptedException {
        //this.checkQueue();
        if (this.maxQueueLength < this.waitingQueue.size()){
            this.maxQueueLength = this.waitingQueue.size();
        }
        this.queueLengthJobArrives += this.waitingQueue.size();
        try {
            this.waitingQueue.add(job);
        }
        catch (IllegalStateException e){
            System.out.println("Queue full");
            this.numberJobsLeftEarly++;
        }
    }

    public Job dispatchFromQueue() {
        Job job = this.waitingQueue.remove();
        return job;
    }

    public Queue<Job> getWaitingQueue(){
        return this.waitingQueue;
    }

    public void checkQueue(){
        for (Job job: waitingQueue) {
            if (System.currentTimeMillis()/1000.0 / job.getTimer() > 6){
                System.out.println("Job leaving early...");
                this.waitingQueue.remove(job);
                this.numberJobsLeftEarly++;
            }
        }
    }
}
