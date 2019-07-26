package be.xplore.recordreplay.repo;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;

public interface HttpClient {

    Response execute(Request request);

}
