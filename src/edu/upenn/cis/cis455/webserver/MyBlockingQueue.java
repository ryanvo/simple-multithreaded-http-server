package edu.upenn.cis.cis455.webserver;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by ryan on 9/29/16.
 */
public class MyBlockingQueue<T> {

        private Queue<T> queue = new ArrayDeque<T>();
        private int size;

        public MyBlockingQueue(int size) {
            this.size = size;
        }

        public synchronized void put(T request) {

            try{
                while(queue.size() == size)
                    wait();
            } catch (InterruptedException e) {
                //TODO handle?
            }

        }

}
