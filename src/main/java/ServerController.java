package main.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alexiaborchgrevink on 11/22/17.
 */
public class ServerController {

    public Gamma gamma1;
    public Gamma gamma2;
    public Exponential exponential;
    public Uniform uniform;
    private SimulationSystem system;
    private ArrayList<Distribution> servers;
    private double waitJobTime;
    private double maxWaitTime;

    public ServerController(Gamma gamma1, Gamma gamma2, Exponential exponential, Uniform uniform, SimulationSystem system){
        this.system=system;
        servers = new ArrayList<>(4);
        this.gamma1=gamma1;
        this.gamma2=gamma2;
        this.exponential=exponential;
        this.uniform=uniform;
        this.servers.add(gamma1);
        this.servers.add(gamma2);
        this.servers.add(exponential);
        this.servers.add(uniform);
        this.waitJobTime = 0.0;
        this.maxWaitTime = 0.0;
    }

    public boolean checkServersBusy() {
        boolean allBusy = false;
        if (gamma1.isLocked() && gamma2.isLocked() && uniform.isLocked() && exponential.isLocked()) {
            allBusy = true;
        }
        return allBusy;
    }

    public Distribution assignServer() throws InterruptedException {
        double start = System.currentTimeMillis();
        Distribution server = null;
        //if (!checkServersBusy()) {
            Random random = new Random();
            boolean exit = false;
            int chooseServer = random.nextInt(4);
            if (servers.get(chooseServer).isLocked()) {
                do {
                    chooseServer = random.nextInt(4);
                }
                while (servers.get(chooseServer).isLocked());
                if (!servers.get(chooseServer).isLocked()) {
                    server = servers.get(chooseServer);
                }
            }
            else{
                server = servers.get(chooseServer);
            }
        //} else {
         /*   this.system.queueLengthJobArrives = this.system.waitingQueue.size();
            if (this.system.queueLengthJobArrives > this.system.maxQueueLength) {
                this.system.maxQueueLength = this.system.queueLengthJobArrives;
            }
        }*/
        double done = System.currentTimeMillis();
        if (this.maxWaitTime < this.waitJobTime){
            this.maxWaitTime = this.waitJobTime;
        }
        this.waitJobTime += done-start/1000;
        return server;
    }

    public double getWaitJobTime(){
        return this.waitJobTime;
    }

    public double getMaxWaitTime(){
        return this.maxWaitTime;
    }
}

