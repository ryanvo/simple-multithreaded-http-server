#!/bin/sh

java -classpath lib/*:bin/.:./target/WEB-INF/lib/webserver.jar webserver.HttpServer 80 www &
sleep infinity