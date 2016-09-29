package edu.upenn.cis.cis455.webserver;
import org.apache.log4j.Logger;


public class MyPoolThread extends Thread {

    static Logger log = Logger.getLogger(MyPoolThread.class);

    private final MyBlockingQueue pool;

    public MyPoolThread(MyBlockingQueue pool) {
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
            log.error("Thread Exception" + e.toString());
        } finally {
            log.info("Thread Exited");
        }
    }

}
