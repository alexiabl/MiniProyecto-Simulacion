package main.java;

/**
 * Created by alexiaborchgrevink on 11/26/17.
 */
public class Job2 {

    private int id;
    private Distribution server;
    private double arrivalTime;
    private double serviceStartTime;
    private double finishTime;

    public Job2(int id){
        this.id=id;
        this.server = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Distribution getServer() {
        return server;
    }

    public void setServer(Distribution server) {
        this.server = server;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(double serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }
}
