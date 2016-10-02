package edu.upenn.cis.cis455.webserver;


import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.nio.file.Files;

public class HttpServlet {

    static Logger log = Logger.getLogger(HttpServlet.class);

    private String HTTP_VERSION = "HTTP/1.0";
    private String NOT_FOUND_MESSAGE = "<html><body><h1>404 File Not Found</h1></body></html>";
    private String SHUTDOWN_MESSAGE = "<html><body>Server shutting down...</body></html>";

    private final String rootDirectory;
    private HttpRequestManager manager;
    private ServerSocket serverSocket;

    public HttpServlet(String rootDirectory, HttpRequestManager manager) {
        this.rootDirectory = rootDirectory;
        this.manager = manager;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void service(HttpRequestMessage request, HttpResponseMessage response) {
        manager.update(Thread.currentThread().getId(), request.getRequestURI().toString());

        log.info(String.format("Thread ID %d is Serving URI %s", Thread.currentThread().getId(),
                request.getRequestURI()));

        switch (request.getType()) {
            case "get":
                doGet(request, response);
                break;
            case "control":
                doControl(response);
                break;
            case "shutdown":
                doShutdown(response);
                break;
            default:
                log.error("HttpServlet Did Not Recognize Request Type");
                manager.update(Thread.currentThread().getId(), "waiting");
                throw new IllegalStateException();
        }

        manager.update(Thread.currentThread().getId(), "waiting");
    }

    public void doGet(HttpRequestMessage request, HttpResponseMessage response) {
        File fileRequested = new File(rootDirectory + request.getRequestURI().getPath());

        if (fileRequested.canRead()) {

            log.info(String.format("HttpServlet Serving GET Request for %s", fileRequested.getName()));

            response.setVersion(HTTP_VERSION);
            response.setStatusCode("200");
            response.setErrorMessage("OK");

            try {
                response.setContentType(Files.probeContentType(fileRequested.toPath()));
            } catch (IOException e) {
                log.error("Error Reading File to Determine Content-Type", e);
            }
            response.setContentLength(Long.valueOf(fileRequested.length()).intValue());

        } else {
            log.info(String.format("%s Not found", fileRequested.getName()));

            response.setVersion(HTTP_VERSION);
            response.setStatusCode("404");
            response.setErrorMessage("Not Found");
            response.setContentType("text/html");
            response.setContentLength(NOT_FOUND_MESSAGE.length());

        }
        log.debug(response.getStatusAndHeader());

        try (PrintWriter writer = new PrintWriter(response.getOutputStream())) {
            writer.println(response.getStatusAndHeader());
            writer.flush();

            if (fileRequested.canRead()) {
                Files.copy(fileRequested.toPath(), response.getOutputStream());
                log.info(String.format("%s Sent to Client", fileRequested.getName()));
            } else {
                writer.println(NOT_FOUND_MESSAGE);
                log.info(String.format("Not Found Error Sent to Client", request.getRequestURI()
                        .getPath()));
            }

        } catch (IOException e) {
            log.error("Could Not Write GET Response to Socket", e);

        }
    }

    public void doControl(HttpResponseMessage response) {

        log.info("HttpServlet Serving Control Page Request");
        String controlPageHtml = manager.getHtmlResponse();
        response.setVersion(HTTP_VERSION);
        response.setStatusCode("200");
        response.setErrorMessage("OK");
        response.setContentType("text/html");
        response.setContentLength(controlPageHtml.length());

        log.debug(response.getStatusAndHeader());

        try (PrintWriter writer = new PrintWriter(response.getOutputStream(), true)) {
            writer.println(response.getStatusAndHeader());
            writer.println(manager.getHtmlResponse());
        } catch (IOException e) {

            log.error("Could Not Write Control Page Response to Socket", e);
        }

        log.info("Wrote Control Page Response to Socket");
    }

    public void doShutdown(HttpResponseMessage response) {

        log.info("HttpServlet Serving Shutdown Request");

        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error("Could not close socket. Server will not shutdown");
        }
        log.info("Server Socket Closed");
        manager.issueShutdown();

        response.setVersion(HTTP_VERSION);
        response.setStatusCode("200");
        response.setErrorMessage("OK");
        response.setContentType("text/html");
        response.setContentLength(SHUTDOWN_MESSAGE.length());


        try(PrintWriter writer = new PrintWriter(response.getOutputStream(), true)) {

            log.debug(response.getStatusAndHeader());

            writer.println(response.getStatusAndHeader());
            writer.println(SHUTDOWN_MESSAGE);

        } catch (IOException e) {
            log.error("Could Not Write Shutdown HTML Page to Socket", e);
        }

    }

}
