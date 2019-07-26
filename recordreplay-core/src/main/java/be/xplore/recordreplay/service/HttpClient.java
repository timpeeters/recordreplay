package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;

public interface HttpClient {

    Response execute(Request request);

}
