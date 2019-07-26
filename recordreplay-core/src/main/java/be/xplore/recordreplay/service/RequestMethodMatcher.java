package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

public class RequestMethodMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getMethod().equals(otherRequest.getMethod())) {
            return new Result(0);
        } else {
            return new Result(1);
        }
    }
}
