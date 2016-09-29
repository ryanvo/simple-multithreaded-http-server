package edu.upenn.cis.cis455.webserver;

public class MultiThreadedServerFactory {


    static MultiThreadedServer create(int poolSize, int workQueueSize) {

        MyBlockingQueue workQueue = new MyBlockingQueue(workQueueSize);
        MyExecutorService exec = new MyExecutorService(poolSize, workQueue);

        return new MultiThreadedServer(exec, new HttpServlet());
    }

}
