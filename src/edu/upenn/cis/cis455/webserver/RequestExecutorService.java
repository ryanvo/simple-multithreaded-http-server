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

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                shutdown();
            }
        };

        for (int i = 0; i < poolSize; i++) {
            threadPool.add(new PooledThread(queue));
        }

        for (Thread thread : threadPool) {
            thread.start();
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    }

    public void execute(Runnable request) throws IllegalStateException{
        if (isShutdown) {
            throw new IllegalStateException("Executor Service is stopped");
        }
        queue.put(request);
    }

    public void shutdown() {
        isShutdown = true;
        for (Thread thread : threadPool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.interrupt();
                log.error("Interrupted Exception");
            }
        }
        log.info("All threads stopped");
    }

    public boolean isRunning() {
        return !isShutdown;
    }

}
