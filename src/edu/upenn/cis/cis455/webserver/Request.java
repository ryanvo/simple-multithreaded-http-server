package edu.upenn.cis.cis455.webserver;

import java.net.Socket;

public class Request implements Runnable {

    public Request(Socket socket) {



    }

    @Override
    public void run() {


        handle();
    }

    void handle() {

    };


}


