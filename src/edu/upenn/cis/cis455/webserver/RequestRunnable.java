package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandlerRunnable implements Runnable {
    static Logger log = Logger.getLogger(RequestHandlerRunnable.class);

    final private Socket connection;

    public RequestHandlerRunnable(Socket connection) {

        this.connection = connection;

    }

    @Override
    public void run() {
        handle(connection);
    }

    private void handle(Socket connection) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection
                        .getInputStream()))
        ) {
            String request = bufferedReader.readLine();
            System.out.printf("Client Sent: %s\n", request);
            log.info("Client sent: " + request);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("IOException");
        }
    }


}


