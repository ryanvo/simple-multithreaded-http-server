package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

public class RequestRunnable implements Runnable {
    static Logger log = Logger.getLogger(RequestRunnable.class);

    private Request request;

    public RequestRunnable(Request request) {

        this.request = request;

    }

    @Override
    public void run() {
        handle(request);
    }

    private void handle(Request request) {
//        try (
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request
//                        .getInputStream()))
//        ) {
//            String req = bufferedReader.readLine();
//            System.out.printf("Client Sent: %s\n", req);
//            log.info("Client sent: " + request);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.info("IOException");
//        }
    }


}


