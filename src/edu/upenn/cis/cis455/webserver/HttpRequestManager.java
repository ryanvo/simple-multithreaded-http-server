package edu.upenn.cis.cis455.webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class HttpRequestManager {

    private Map<Integer, String> idToUri;
    private Set<Thread> threadPool;

    public HttpRequestManager(Set<Thread> threadPool) {
        idToUri = new HashMap<Integer, String>();

    }

    public void update(long threadId, String uri) {
        idToUri.putIfAbsent((int) threadId, uri);

    }

    public void issueShutdown() {



    }

    public String getHtmlResponse() {

        StringBuilder html = new StringBuilder();

        html.append("<html><body><h1>Control Panel</h1>")
                .append("</body></html>");

        return html.toString();
    }

}
