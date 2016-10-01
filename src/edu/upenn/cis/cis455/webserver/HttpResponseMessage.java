package edu.upenn.cis.cis455.webserver;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HttpResponseMessage {

    private final String SERVER_HEADER = "Server: ryanvo/55.5";
    private String version;
    private String statusCode;
    private String errorMessage;
    private String date;
    private String contentType;
    private int contentLength;

    private final Socket connection;

    public HttpResponseMessage(Socket connection) {
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

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }

    public String getStatusAndHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(version).append(" ").append(statusCode).append(" ").append(errorMessage).append('\n')
                .append(date).append('\n')
                .append("Content-Type: ").append(contentType).append('\n')
                .append(contentLength).append('\n')
                .append(SERVER_HEADER).append('\n')
                .append("Connection: close").append('\n');
        return sb.toString();
    }

    public static String getHttpDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

}
