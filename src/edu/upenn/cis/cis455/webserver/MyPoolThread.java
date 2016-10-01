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
        try{
            log.info("Thread Started");
            while (!isInterrupted()) {
                pool.take().run();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Thread Exception ");
        } finally {
            log.info("Thread Exited");
        }
    }

}
