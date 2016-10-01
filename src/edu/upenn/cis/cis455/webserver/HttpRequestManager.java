package edu.upenn.cis.cis455.webserver;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestManager {

    private Map<Long, String> idToUri;
    private final MyExecutorService executorService;

    public HttpRequestManager(MyExecutorService executorService) {
        idToUri = new HashMap<>();
        this.executorService = executorService;
        for (Thread thread : executorService.threadPool()) {
            update(thread.getId(), "waiting");
        }
    }

    public void update(long threadId, String uri) {
        idToUri.put(threadId, uri);
}

    public void issueShutdown() {
        executorService.shutdown();
    }

    public String getHtmlResponse() {
        StringBuilder html = new StringBuilder();

        html.append("<html><body><h1>Control Panel</h1>")
                .append("<p><h2>Thread &nbsp; &nbsp; &nbsp; &nbsp;Running</h2></p>");


        for (Thread thread : executorService.threadPool()) {
            long tid = thread.getId();
            html.append("<p>").append(tid).append("&nbsp; &nbsp; &nbsp; &nbsp &nbsp; &nbsp; " +
                    "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;").append(idToUri.get(tid));
        }

        html.append("<p><a href=\"/shutdown/\">Shutdown</a></p></body></html>");
        return html.toString();
    }

}
