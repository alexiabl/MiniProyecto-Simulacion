package main.java;

import java.lang.System;
import java.util.concurrent.ExecutionException;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Controller {

    public static void main (String args[]) throws InterruptedException, ExecutionException {
        Simulation simulation = new Simulation();
        double arrivalRate = simulation.getExponentialArrivalRate();
        simulation.runSimulation(1000, arrivalRate);
    }
}
