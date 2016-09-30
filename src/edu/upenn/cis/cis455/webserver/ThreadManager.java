package edu.upenn.cis.cis455.webserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryan on 9/30/16.
 */
public class ThreadManager {

    private MyExecutorService executorService;
    private Map<Integer, String> idToUri;

    public ThreadManager(MyExecutorService executorService) {
        this.executorService = executorService;
        idToUri = new HashMap<Integer, String>();
    }

    public void update(long threadId, String uri) {
        idToUri.putIfAbsent((int) threadId, uri);

    }

}
