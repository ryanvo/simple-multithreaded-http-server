package edu.upenn.cis.cis455.webserver;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class HttpServer {

	/**
	 * Logger for this particular class
	 */
    static Logger log = Logger.getLogger(HttpServer.class);

	public static void main(String args[])
	{

        final int PORT = 8080;
        int POOL_SIZE = 8;
        int WORK_QUEUE_SIZE = 16;


        BasicConfigurator.configure();



        MultiThreadedServer server = MultiThreadedServerFactory.create(POOL_SIZE, WORK_QUEUE_SIZE);

        server.start(PORT);
        System.exit(0);
    }

}
