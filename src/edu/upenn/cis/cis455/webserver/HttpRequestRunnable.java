package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class HttpRequestRunnable implements Runnable {
    static Logger log = Logger.getLogger(HttpRequestRunnable.class);

    private Socket connection;
    private HttpServlet servlet;

    public HttpRequestRunnable(Socket connection, HttpServlet servlet) {
        this.servlet = servlet;
        this.connection = connection;
    }

    @Override
    public void run() {
        servlet.service(new HttpRequestMessage(connection), new HttpResponseMessage(connection));
        try {
            connection.close();
        } catch (IOException e) {
            log.error("Could not close socket");
        }
    }

}


