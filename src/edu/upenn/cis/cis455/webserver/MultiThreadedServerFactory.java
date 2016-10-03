package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

/**
 * Assembles the components of the multithreaded server
 */
public class MultiThreadedServerFactory {

    static Logger log = Logger.getLogger(MultiThreadedServerFactory.class);

    static MultiThreadedServer create(String rootDirectory, int poolSize, int workQueueSize) {

        MyBlockingQueue workQueue = new MyBlockingQueue(workQueueSize);
        MyExecutorService exec = new MyExecutorService(poolSize, workQueue);
        HttpRequestManager manager = new HttpRequestManager(exec);
        HttpServlet servlet = new HttpServlet(rootDirectory, manager);

        log.info(String.format("Factory Created Server at %s, %d threads, request queue of %d",
                rootDirectory, poolSize, workQueueSize));

        return new MultiThreadedServer(exec, servlet);
    }

}
