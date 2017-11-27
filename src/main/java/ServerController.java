package main.java;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
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
    private long waitJobTime;
    private long maxWaitTime;

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
    }

    public Distribution assignServer() throws InterruptedException {
        long start = System.currentTimeMillis();
        Distribution server = null;
            Random random = new Random();
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
        long end = System.currentTimeMillis();
        long timer = end-start;
        this.waitJobTime += timer;
        if (this.maxWaitTime < timer){
            this.maxWaitTime = timer;
        }
        return server;
    }

    public long getWaitJobTime(){
        return this.waitJobTime;
    }

    public long getMaxWaitTime(){
        return this.maxWaitTime;
    }

    public double calculateProbOneServerAvailable() {
        return 0;
    }

    public double calculateProbTwoServersAvailable() {

        return 0;
    }

}

