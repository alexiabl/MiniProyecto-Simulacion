package main.java;

import java.time.Duration;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class System {
    public Duration waitingTimeForJob;
    public Duration responseTime;
    public double queueLengthJobArrives;
    public Duration waitTime;
    public double queueLength;
    public double probOneServerAvailable;
    public double probTwoServersAvailable;
    public Duration timeServerIdle;
    public double numberJobsRemainingAfter;
    public double numberJobsLeftEarly;
    public double numberJobsByServer;
    private ArrayBlockingQueue<Job> waitingQueue;

    public System() {
        waitingQueue = new ArrayBlockingQueue<Job>(1000);
    }

    public Duration calculateExpWaitingTimeJob() {
        return null;
    }

    public Duration calculateExpResponseTime() {
        return null;
    }

    public double calculateExpQueueLengthOnArrival() {
        return 0;
    }

    public Duration calculateMaxWaitTime() {
        return null;
    }

    public double calculateMaxQueueLength() {
        return 0;
    }

    public double calculateExpNumberJobsPerServer() {
        return 0;
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
