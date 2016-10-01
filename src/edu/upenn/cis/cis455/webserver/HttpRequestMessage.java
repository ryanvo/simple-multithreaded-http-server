package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HttpRequestMessage {

    static Logger log = Logger.getLogger(HttpRequestMessage.class);

    private boolean isShutdown;
    private File fileRequested;
    private String requestURL;
    private Map<String, String> headers;

    private Socket connection;
    private String method;
    private String uri;
    private String type;
    private String contentType;
    private String contentLength;

    private InputStream inputStream;


    public HttpRequestMessage(Socket connection) {
        this.connection = connection;
        parseRequest(connection);
    }

    public String getType() {
        return type;
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
        try  {

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream
                    ()));
            log.info("Parsing HTTP Request");
            String line = in.readLine();
            log.info("Status: " + line);

            String[] statusLine = line.split(" ");
            method = statusLine[0];
            uri = statusLine[1];

            if (uri.equals("/shutdown") || uri.equals("/shutdown/")) {
               type = "shutdown";
            } else if (uri.matches("/*control/*")) {
                type = "control";
            } else {
                type = "else";
            }

        } catch (IOException e) {
            log.error("Socket IOException");
        }
        log.info(String.format("HttpRequestMessage parsed with Method: %s, Uri: %s", method, uri));
    }

}
