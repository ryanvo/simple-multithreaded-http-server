package edu.upenn.cis.cis455.webserver;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpResponse {

    final private String server = "ryanvo/9.30";
    final private String connectionClose = "Connection: close";

    private String version;
    private String statusCode;
    private String errorMessage;
    private String date;
    private String contentType;
    private String contentLength;

    private final Socket connection;

    public HttpResponse(Socket connection) {
        this.connection = connection;
        date = getHttpDate();
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }

    public static String getHttpDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

}
