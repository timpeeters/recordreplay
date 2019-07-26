package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;

public interface HttpClient {

    Response execute(Request request);

}
