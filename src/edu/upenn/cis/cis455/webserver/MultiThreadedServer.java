package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MultiThreadedServer {

    static Logger log = Logger.getLogger(MultiThreadedServer.class);

    final private MyExecutorService exec;
    final private HttpServlet servlet;

    public MultiThreadedServer(MyExecutorService exec, HttpServlet servlet) {
        this.exec = exec;
        this.servlet = servlet;
    }

    public void start(int port) {

        try {
            ServerSocket socket = new ServerSocket(port);
            servlet.setServerSocket(socket);
            log.info(String.format("HTTP Server Started on Port %d", port));
            while (exec.isRunning()) {
                try {
                    Socket connection = socket.accept();
                    exec.execute(new HttpRequestRunnable(connection, servlet));
                } catch (IllegalStateException e) {
                    log.error("Socket Created Between Client But Executor is Stopped");
                }
            }
        } catch (SocketException e) {
            log.info("ServerSocket Closed Due To Shutdown Request");
        } catch (IOException e) {
            log.error("HTTP Server Could Not Open Port " + port, e);
        }

        log.info("Server Successfully Shutdown");
    }

}