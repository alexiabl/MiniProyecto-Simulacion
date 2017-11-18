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

    public CustomThreadFactory(String name){
        this.name = name;
    }
    @Override
    public Thread newThread(Runnable r) {
        this.counter++;
        return new Thread(r, name+"-"+counter);
    }


}
