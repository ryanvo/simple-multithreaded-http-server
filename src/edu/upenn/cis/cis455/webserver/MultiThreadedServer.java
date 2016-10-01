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

    public void start(int port) {

        try (ServerSocket socket = new ServerSocket(port)) {
            log.info(String.format("HTTP Server Started on Port %d", port));
            while (exec.isRunning()) {
                final Socket connection = socket.accept();
                try {
                    exec.execute(new HttpRequestRunnable(connection, servlet));
                } catch (IllegalStateException e) {
                    log.info("Server Shutdown"); //TODO handle this bc its not rly down yet
                }
            }
        } catch (IOException e) {
            log.error("HTTP Server Could Not Open Port " + port, e);
        }
    }

    public void stop() {
        exec.shutdown();
    }

}