# Multithreaded HTTP Server
### Introduction
This is a simple HTTP server. Each request is served by a thread from a thread pool. If all threads in the pool are active, the request is put onto a blocking queue and served when a thread returns back to the availability pool. This project solves the producer-consumer problem.

The Java concurrency library was not used. The executor service, blocking queue, and all HTTP handles were self-implemented.

### Demo
[View live demo](http://demo.rvo.space)

### Run
```sh
$ ant build
$ sh runserver.sh
```
