package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

public class RequestPathMatcher implements RequestMatcher{

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getPath().equals(otherRequest.getPath())) {
            return new Result(0);
        } else {
            return new Result(1);
        }
    }
}
