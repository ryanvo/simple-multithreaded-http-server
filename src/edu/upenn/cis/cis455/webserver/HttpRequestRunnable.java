package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

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
    public void run() throws RuntimeException {
        HttpRequest request = new HttpRequest(connection);

        if (request.isShutdown()) {
            throw new RuntimeException();
        } else {
            servlet.service(request, new HttpResponse());
        }
    }



}


