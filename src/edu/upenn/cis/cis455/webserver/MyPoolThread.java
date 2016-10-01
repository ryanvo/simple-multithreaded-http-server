package edu.upenn.cis.cis455.webserver;
import org.apache.log4j.Logger;


public class MyPoolThread extends Thread {

    static Logger log = Logger.getLogger(MyPoolThread.class);

    private final MyBlockingQueue pool;
    private boolean isShutdown = false;

    public MyPoolThread(MyBlockingQueue pool) {
        this.pool = pool;
    }

    @Override
    public void run() {

        log.info("Thread Started");

        while (!isInterrupted() && !isShutdown) {
            pool.take().run();
        }

        log.info("Thread Closed");
    }

    public void shutdown() {
        isShutdown = true;
    }
}
