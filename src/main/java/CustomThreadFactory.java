package main.java;

import java.lang.*;
import java.lang.System;

import java.util.concurrent.ThreadFactory;

/**
 * Created by alexiaborchgrevink on 11/17/17.
 */
public class CustomThreadFactory implements ThreadFactory {

    private String name;
    private int counter;
    private int maxThreads;

    public CustomThreadFactory(String name, int maxThreads){
        this.name = name;
        this.maxThreads=maxThreads;
    }

    @Override
    public Thread newThread(Runnable r) {
        this.counter++;
        return new Thread(r, name+"-"+counter);
    }

    public int getCounter(){
        return this.counter;
    }


}
