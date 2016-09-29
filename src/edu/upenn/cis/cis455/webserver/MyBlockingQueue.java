package edu.upenn.cis.cis455.webserver;

import java.util.ArrayDeque;
import java.util.Queue;


public class MyBlockingQueue<E> {

    private Queue<E> queue = new ArrayDeque<E>();
    private int size;

    public MyBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void put(E request) {

        try{
            while (queue.size() == size)
                wait();
        } catch (InterruptedException e) {
            //TODO handle?
        }

    }

    public synchronized E take() {

        try {
            while (queue.isEmpty())
                wait();
        } catch (InterruptedException e) {

            //TODO handle?

        }

        E request = queue.remove();
        notify();
        return request;
    }
}

}
