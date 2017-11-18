package main.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class Uniform implements Distribution {

    private boolean isLocked;
    private int a;
    private int b;

    public Uniform() {

    }

    public Uniform(int a, int b) {
        this.a = a;
        this.b = b;
    }


    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public synchronized double calculateServiceTime(double probability) throws InterruptedException {
        this.isLocked = true;
        double serviceTime = a + (b - a) * probability;
        this.isLocked = false;
        return serviceTime;
    }

    @Override
    public boolean isLocked(){
        return this.isLocked;
    }
}
