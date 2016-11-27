package webserver;

import org.apache.log4j.Logger;
import webserver.concurrent.MyBlockingQueue;
import webserver.concurrent.MyExecutorService;
import webserver.http.HttpRequestManager;
import webserver.http.HttpServlet;

/**
 * Assembles the components of the multithreaded server
 */
public class MultiThreadedServerFactory {

    static Logger log = Logger.getLogger(MultiThreadedServerFactory.class);

    static public MultiThreadedServer create(String rootDirectory, int poolSize, int workQueueSize) {

        MyBlockingQueue workQueue = new MyBlockingQueue(workQueueSize);
        MyExecutorService exec = new MyExecutorService(poolSize, workQueue);
        HttpRequestManager manager = new HttpRequestManager(exec);
        HttpServlet servlet = new HttpServlet(rootDirectory, manager);

        log.info(String.format("Factory Created Server at %s, %d threads, request queue of %d",
                rootDirectory, poolSize, workQueueSize));

        return new MultiThreadedServer(exec, servlet);
    }

}
