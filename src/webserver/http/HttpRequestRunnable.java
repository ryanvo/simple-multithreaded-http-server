package webserver.http;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

public class HttpRequestRunnable implements Runnable {

    static Logger log = Logger.getLogger(HttpRequestRunnable.class);

    private Socket connection;
    private HttpServlet servlet;

    /**
     * Carries a connection to a client with a request
     * @param connection socket to client
     * @param servlet to handle request
     */
    public HttpRequestRunnable(Socket connection, HttpServlet servlet) {
        this.servlet = servlet;
        this.connection = connection;
    }

    /**
     * Tells servlet to handle the request and closes the socket once the request is served
     */
    @Override
    public void run() {
        try {
            servlet.service(new HttpRequestMessage(connection), new HttpResponseMessage
                    (connection));
        } catch (IllegalStateException e) {
            log.error("Invalid Request Ignored", e);
        } catch (URISyntaxException e) {
            log.error("URI Could Not Be Processed", e);
        }

        try {
            connection.close();
            log.info("Socket Closed");
        } catch (IOException e) {
            log.error("Could Not Close Socket After Sending Response", e);
        }
    }

}


