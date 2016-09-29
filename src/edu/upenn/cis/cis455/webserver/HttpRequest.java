package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HttpRequest {

    static Logger log = Logger.getLogger(HttpRequest.class);


    private boolean isShutdown;
    private String header;
    private File fileRequested;
    private String requestURL;
    private String method;
    private Map<String, String> headers;
    private Socket connection;

    private InputStream inputStream;


    public HttpRequest(Socket connection) {
        this.connection = connection;
        init(connection);
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuilder getRequestURL() {
        return null;
    }

    public String getMethod() {
        return method;
    }


    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    private void init(Socket connection) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        ) {



        } catch (IOException e){
            log.error("Socket IOException");
        } finally {
            log.info("HttpRequest was read from socket.");
        }


    }

}
