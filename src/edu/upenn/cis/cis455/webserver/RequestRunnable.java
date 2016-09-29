package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.Logger;

import java.net.Socket;

public class RequestRunnable implements Runnable {
    static Logger log = Logger.getLogger(RequestRunnable.class);

    private Socket connection;

    public RequestRunnable(Socket connection) {

        this.connection = connection;

    }

    @Override
    public void run() throws RuntimeException {
        Request request = RequestBuilder.build(connection);
        if (!handle(request)) {
            throw new RuntimeException();
        }
    }

    private boolean handle(Request request)  {
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
        return true;
    }


}


