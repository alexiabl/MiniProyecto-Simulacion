package main.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alexiaborchgrevink on 11/26/17.
 */
public class SimulationTest {

    private ArrayList<Integer> arrival;
    private ArrayList<Integer> start;
    private ArrayList<Integer> finish;
    private ArrayList<Distribution> servers;
    private int jobNumber;
    private int arrivalTime;
    private int nsimulations;

    public SimulationTest(int nsimulations){
        this.arrival = new ArrayList<>();
        this.start = new ArrayList<>();
        this.finish = new ArrayList<>();
        this.nsimulations = nsimulations;
        servers.add(new Gamma(7,3));
        servers.add(new Gamma(5,2));
        servers.add(new Exponential(0.3));
        servers.add(new Uniform(4,9));
    }

    public void runSimulation(){
        Random random = new Random();
        this.arrivalTime=0;
        while (arrivalTime<600){
            arrivalTime += 30*Math.log(random.nextInt());
            arrival.add(jobNumber,arrivalTime);
            jobNumber++;
            int chooseServer = random.nextInt(4);
            //Distribution server = servers.get(chooseServer);

        }
    }

}
