package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class RequestExecutorService<T> {

    static Logger log = Logger.getLogger(PooledThread.class);

    private MyBlockingQueue queue;
    private volatile boolean isShutdown = false;
    private Set<Thread> threadPool = new HashSet<>();

    public RequestExecutorService(int poolSize, MyBlockingQueue queue) {
        this.queue = queue;

        for (int i = 0; i < poolSize; i++) {
            Thread thread = new Thread(new PooledThread(queue));
            thread.start();
            threadPool.add(thread);
        }
    }

    public void execute(Runnable request) {
        queue.put(request);
    }

    public void shutdown() {
        for (Thread thread : threadPool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.interrupt();
                log.error("Interrupted Exception");
            }
        }
        log.info("All threads stopped");
        isShutdown = true;
    }

    public boolean isRunning() {
        return !isShutdown;
    }

}
