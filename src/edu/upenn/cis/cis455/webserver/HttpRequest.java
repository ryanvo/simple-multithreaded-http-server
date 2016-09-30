package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HttpRequest {

    static Logger log = Logger.getLogger(HttpRequest.class);


    private boolean isShutdown;
    private File fileRequested;
    private String requestURL;
    private Map<String, String> headers;

    private Socket connection;
    private String method;
    private String uri;
    private String contentType;
    private String contentLength;

    private InputStream inputStream;


    public HttpRequest(Socket connection) {
        this.connection = connection;
        parseRequest(connection);
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public String getRequestURI() {
        return uri;
    }

    public String getMethod() {
        return method;
    }


    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    private void parseRequest(Socket connection) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        ) {
            log.info("Reading HttpRequest from socket");

            String line = in.readLine();
            log.info("Status: " + line);

            String[] statusLine = line.split(" ");
            method = statusLine[0];
            uri = statusLine[1];

            isShutdown = (uri.equals("/shutdown") || uri.equals("/shutdown/"));

        } catch (IOException e) {
            log.error("Socket IOException");
        }
        log.info(String.format("HttpRequest parsed with Method: %s, Uri: %s", method, uri));
    }

}
