package edu.upenn.cis.cis455.webserver;
import org.apache.log4j.Logger;


public class PooledThread extends Thread {

    private final MyBlockingQueue pool;
    static Logger log = Logger.getLogger(MultiThreadedServer.class);

    public PooledThread(MyBlockingQueue pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        Throwable thrown = null;
        try{
            log.info("Thread Started");
            while (!isInterrupted()) {
                pool.take().run();
            }
        } catch (Throwable e) {
            thrown = e;
        } finally {
            log.info("Thread Exited");


        }
    }

}
