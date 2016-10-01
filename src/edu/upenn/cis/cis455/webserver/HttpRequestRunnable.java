package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

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
        try {
            servlet.service(new HttpRequestMessage(connection), new HttpResponseMessage
                    (connection));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            log.error("Invalid Request Ignored");
        } catch (URISyntaxException e) {

        }
        try {
            connection.close();
        } catch (IOException e) {
            log.error("Could Not Close Socket After Sending Response");
        }
    }

}


