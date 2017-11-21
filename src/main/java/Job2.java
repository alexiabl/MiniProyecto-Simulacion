package main.java;

/**
 * Created by alexiaborchgrevink on 11/21/17.
 */
public class Job2 {

    private Distribution server;
    private double serviceProbability;
    private double serviceTime;
    private SimulationSystem simulationSystem;
    private double timer;

    public Job2(){

    }

    public Distribution getServer() {
        return server;
    }

    public void setServer(Distribution server) {
        this.server = server;
    }

    public double getServiceProbability() {
        return serviceProbability;
    }

    public void setServiceProbability(double serviceProbability) {
        this.serviceProbability = serviceProbability;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void executeCalculateServiceTime() throws InterruptedException {
        double start=System.currentTimeMillis();
        this.serviceTime = server.calculateServiceTime(this.serviceProbability);
        this.server.addToProcessedJobs();
        System.out.println("The service time " +"with server " + this.server.getClass().getSimpleName() + " is " + this.serviceTime);
        //this.getServer().unlockServer(this);
        this.timer = System.currentTimeMillis()-start/1000.0;
    }


}
