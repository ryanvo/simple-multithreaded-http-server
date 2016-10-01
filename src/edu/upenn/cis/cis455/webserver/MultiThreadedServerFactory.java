package edu.upenn.cis.cis455.webserver;

public class MultiThreadedServerFactory {


    static MultiThreadedServer create(int poolSize, int workQueueSize) {

        MyBlockingQueue workQueue = new MyBlockingQueue(workQueueSize);
        MyExecutorService exec = new MyExecutorService(poolSize, workQueue);
        HttpRequestManager manager = new HttpRequestManager(exec.getThreadPool());
        HttpServlet servlet = new HttpServlet(manager);

        return new MultiThreadedServer(exec, servlet);
    }

}
