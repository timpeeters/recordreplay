package be.xplore.recordreplay.http;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;

import java.net.Proxy;

public interface HttpClient {

    Response execute(Request request);

    HttpClient proxy(Proxy proxy);

}
