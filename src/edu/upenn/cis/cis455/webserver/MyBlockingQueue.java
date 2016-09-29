package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;


public class MyBlockingQueue {

    static Logger log = Logger.getLogger(MyBlockingQueue.class);

    private Queue<Runnable> queue = new ArrayDeque<>();
    private int size;

    public MyBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void put(Runnable request) {

        try{
            synchronized (queue) {
                while (queue.size() == size) {
                    wait();
                }
            }

            synchronized (queue) {
                queue.add(request);
                notify();
            }

        } catch (InterruptedException e) {
            //TODO handle?
            log.error("InterruptedException");
        }

    }

    public Runnable take() {

        try {
            while (queue.isEmpty())
                synchronized (queue) {
                    wait();
                }
        } catch (InterruptedException e) {
            //TODO handle?
            log.error("InterruptedException");
        }

        synchronized (queue) {
            notify();
            return queue.remove();
        }
    }
}

