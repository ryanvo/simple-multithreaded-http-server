package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpRequestMessage {

    static Logger log = Logger.getLogger(HttpRequestMessage.class);

    private String method;
    private URI uri;
    private String type;

    public HttpRequestMessage(Socket connection) throws URISyntaxException {
        parseRequest(connection);
    }

    public String getType() {
        return type;
    }

    public URI getRequestURI() {
        return uri;
    }

    private void parseRequest(Socket connection) throws URISyntaxException {

        String line;
        try  {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            line = in.readLine();

        } catch (IOException e) { //TODO throw at constructor
            log.error("Socket IOException", e);
            return;
        }

        log.info("Parsing HTTP Request: " + line);

        String[] statusLine = line.split(" ");
        method = statusLine[0];
        uri = new URI(statusLine[1]);

        if (uri.getPath().matches("/+control/*$")) {
            type = "control";
        } else if (uri.getPath().matches("/+shutdown/*$")) {
            type = "shutdown";
        } else if (method.equals("GET")) {
            type = "get";
        }

        log.info(String.format("HttpRequestMessage Parsed %s Request with URI %s", method, uri));
    }

}
