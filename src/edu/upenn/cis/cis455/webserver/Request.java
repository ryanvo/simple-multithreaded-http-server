package edu.upenn.cis.cis455.webserver;

import java.io.File;
import java.net.Socket;
import java.util.Map;

public class Request {

    private boolean isShutdown;
    private String header;
    private File fileRequested;
    private String requestURL;
    private String method;
    private Map<String, String> headers;

    public Request(Socket connection) {


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

}
