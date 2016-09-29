package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;


public class MyBlockingQueue {

    static Logger log = Logger.getLogger(MyBlockingQueue.class);

    final private Queue<Runnable> queue = new ArrayDeque<>();
    private int size;

    public MyBlockingQueue(int size) {
        this.size = size;
    }

    public void put(Runnable request) {
        while (queue.size() == size) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                //TODO handle?
                log.error("InterruptedException");
            }

        }

        synchronized (this) {
            queue.add(request);
            notify();
        }

    }

    public Runnable take() {
        try {
            while (queue.isEmpty()) {
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            //TODO handle?
            log.error("InterruptedException");
        }

        synchronized (this) {
            notify();
            return queue.remove();
        }
    }
}

