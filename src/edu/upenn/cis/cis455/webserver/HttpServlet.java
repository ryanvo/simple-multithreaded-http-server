package edu.upenn.cis.cis455.webserver;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

public class HttpServlet {

    static Logger log = Logger.getLogger(HttpServlet.class);

    private HttpRequestManager manager;

    public HttpServlet(HttpRequestManager manager) {
        this.manager = manager;
    }

    public void service(HttpRequestMessage request, HttpResponseMessage response) {

        switch (request.getType()) {
            case "get":
                doGet(request, response);
                break;
            case "shutdown":
                doShutdown();
                break;
            case "control":
                doControl(response);
                break;
            default:
                log.error("Request Type Not Recognized");
        }

    }

    public void doGet(HttpRequestMessage request, HttpResponseMessage response) {



    }

    public void doControl(HttpResponseMessage response) {


        String controlPageHtml = manager.getHtmlResponse();
        response.setVersion("HTTP/1.0");
        response.setStatusCode("200");
        response.setErrorMessage("OK");
        response.setContentType("text/html");
        response.setContentLength(String.valueOf(controlPageHtml.length()));

        try {
            PrintWriter out = new PrintWriter(response.getOutputStream(), true);

            log.debug("want to write:\n" + response.getStatusAndHeader());

            out.println(response.getStatusAndHeader());
            out.println("<html><body><h1>Control Panel</h1></body></html>");

            log.info("is here!!!");

        } catch (IOException e) {

            log.error("Could not write response to socket");
            e.printStackTrace();
        }
        log.info("Wrote Response to Socket");
    }

    public void doShutdown() {
        manager.issueShutdown();
    }

}
