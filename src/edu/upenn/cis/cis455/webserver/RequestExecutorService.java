package edu.upenn.cis.cis455.webserver;

public class RequestExecutorService<T> {

    public RequestExecutorService<T>(int poolSize, MyBlockingQueue<T> queue) {

        for (int i = 0; i < poolSize; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        queue.take().run();
                    }
                }
            }).start();

    }

    public void execute(Runnable request) {
        queue.put(request);
    }
}
