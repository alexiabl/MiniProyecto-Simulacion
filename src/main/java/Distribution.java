package main.java;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public interface Distribution {

    public double calculateServiceTime(double probability) throws InterruptedException;

    public boolean isLocked();

    public void unlockServer();

    public void addToProcessedJobs();

    public int getNumberProcessedJobs();

    public double getIdle();

    public void lockServer();

}
