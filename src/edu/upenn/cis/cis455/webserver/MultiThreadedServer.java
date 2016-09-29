package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {

    static Logger log = Logger.getLogger(MultiThreadedServer.class);

    final private RequestExecutorService<Runnable> exec;

    public MultiThreadedServer(RequestExecutorService exec) {
        this.exec = exec;
    }

    public void start(int port) throws IllegalStateException {

        try (ServerSocket socket = new ServerSocket(port)) {
            log.info("HTTP Server STARTED");
            while (exec.isRunning()) {
                final Socket connection = socket.accept();
                exec.execute(new RequestRunnable(connection));
            }
        } catch (IOException e) {
            log.error("IOException");
            e.printStackTrace();
        } finally {
            log.info("Server STOPPED");
        }

    }

    public void stop() {
        exec.shutdown();
    }

}