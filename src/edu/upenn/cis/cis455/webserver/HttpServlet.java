package edu.upenn.cis.cis455.webserver;


import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.file.Files;

public class HttpServlet {

    static Logger log = Logger.getLogger(HttpServlet.class);

    private String HTTP_VERSION = "HTTP/1.0";
    private String NOT_FOUND_PAGE = "<html><body><h1>404 File Not Found</h1></body></html>";
    private HttpRequestManager manager;

    public HttpServlet(HttpRequestManager manager) {
        this.manager = manager;
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
        File fileRequested = new File(request.getRequestURI().getPath());

        log.info(String.format("HttpServlet Serving GET Request for %s", fileRequested.getName()));

        if (fileRequested.canRead()) {
            response.setVersion(HTTP_VERSION);
            response.setStatusCode("200");
            response.setErrorMessage("OK");
            response.setContentType(URLConnection.guessContentTypeFromName(fileRequested.getName()));
            response.setContentLength(Long.valueOf(fileRequested.length()).intValue());
        } else {
            response.setVersion(HTTP_VERSION);
            response.setStatusCode("404");
            response.setErrorMessage("Not Found");
            response.setContentType("text/html");
            response.setContentLength(NOT_FOUND_PAGE.length());
        }

        try (PrintWriter writer = new PrintWriter(response.getOutputStream())) {

            writer.println(response.getStatusAndHeader());

            if (fileRequested.canRead()) {
                Files.copy(fileRequested.toPath(), response.getOutputStream());

                log.info(String.format("%s Sent to Client", fileRequested.getName()));

            } else {
                writer.print(NOT_FOUND_PAGE);

                log.info(String.format("GET Request %s Not Found", request.getRequestURI()
                        .getPath()));
            }

            log.info("Served Get Request");

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

        String message = "<html>Server shutting down...</html>";
        response.setVersion(HTTP_VERSION);
        response.setStatusCode("200");
        response.setErrorMessage("OK");
        response.setContentType("text/html");
        response.setContentLength(message.length());

        try {
            PrintWriter writer = new PrintWriter(response.getOutputStream(), true);

            writer.println(response.getStatusAndHeader());
            writer.println(message);
        } catch (IOException e) {

            log.error("Could Not Write Shutdown HTML Page to Socket", e);

        }

        try {
            response.getOutputStream().close();
        } catch (IOException e) {

            log.error("Could Not Close Socket After Sending Shutdown HTML Page", e);

        }

        manager.issueShutdown();
    }

}
