package edu.upenn.cis.cis455.webserver;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;

public class MyExecutorService {

    static Logger log = Logger.getLogger(MyPoolThread.class);

    private volatile boolean isRunning = true;
    private MyBlockingQueue queue;
    private Set<MyPoolThread> threadPool = new HashSet<>();

    public MyExecutorService(int poolSize, MyBlockingQueue queue) {
        this.queue = queue;

        for (int i = 0; i < poolSize; i++) {
            threadPool.add(new MyPoolThread(queue));
        }

        for (Thread thread : threadPool) {
            thread.start();
        }
    }

    public void execute(Runnable request) throws IllegalStateException {
        if (!isRunning) {
            throw new IllegalStateException("Executor Service is stopped");
        }
        queue.put(request);
    }

    public void shutdown() {
        isRunning = false;


        while (!queue.isEmpty()) { //TODO graceful shutdown
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (MyPoolThread thread : threadPool) {
            thread.shutdown();
        }


        for (MyPoolThread thread : threadPool) {
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                thread.interrupt();
                log.error("Interrupted Exception");
            }
        }

        log.info("All threads stopped");
    }

    public Set<MyPoolThread> threadPool() {
        return threadPool;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
