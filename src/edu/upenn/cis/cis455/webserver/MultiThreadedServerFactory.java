package edu.upenn.cis.cis455.webserver;

public class MultiThreadedServerFactory {


    static MultiThreadedServer create(int poolSize, int workQueueSize) {

        MyBlockingQueue workQueue = new MyBlockingQueue(workQueueSize);
        RequestExecutorService<Runnable> exec = new RequestExecutorService<>(poolSize, workQueue);

        return new MultiThreadedServer(exec);
    }

}
