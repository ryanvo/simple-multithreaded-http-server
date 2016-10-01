package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class HttpServer {

    static Logger log = Logger.getLogger(HttpServer.class);

    public static void main(String args[]) {

        int POOL_SIZE = 8;
        int WORK_QUEUE_SIZE = 16;

        int port = Integer.valueOf(args[0]);
        String rootDirectory = args[1];

        BasicConfigurator.configure();

        MultiThreadedServer server = MultiThreadedServerFactory.create(rootDirectory, POOL_SIZE,
                WORK_QUEUE_SIZE);
        server.start(port);

        System.exit(0);
    }

}
