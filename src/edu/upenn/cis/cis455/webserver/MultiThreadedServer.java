package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {

    static Logger log = Logger.getLogger(MultiThreadedServer.class);

    final private MyExecutorService exec;
    final private HttpServlet servlet;

    public MultiThreadedServer(MyExecutorService exec, HttpServlet servlet) {
        this.exec = exec;
        this.servlet = servlet;
    }

    public void start(int port) throws IllegalStateException {

        try (ServerSocket socket = new ServerSocket(port)) {
            log.info("HTTP Server STARTED");
            while (exec.isRunning()) {
                final Socket connection = socket.accept();
                exec.execute(new HttpRequestRunnable(connection, servlet));
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