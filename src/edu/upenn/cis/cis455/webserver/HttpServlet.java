package edu.upenn.cis.cis455.webserver;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpServlet {

    static Logger log = Logger.getLogger(HttpServlet.class);


    public void service(HttpRequest request, HttpResponse response) {






            try (
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request
                            .getInputStream()))
            ) {
                String req = bufferedReader.readLine();
                System.out.printf("Client Sent: %s\n", req);
                log.info("Client sent: " + request);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("IOException");
            }



    }

    public void doGet(HttpRequest request, HttpResponse response) {


    }

}
