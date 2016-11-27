package webserver.http;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HttpResponseMessage {

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
                .append("Date: ").append(date).append('\n')
                .append("Content-Type: ").append(contentType).append('\n')
                .append("Content-Length: ").append(contentLength).append('\n')
                .append("Connection: Keep-Alive").append('\n');
        return sb.toString();
    }

    private static String getHttpDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

}
