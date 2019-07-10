package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

public class RequestMethodMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getMethod().equals(otherRequest.getMethod())) {
            return new Result().setDistance(0);
        } else {
            return new Result().setDistance(1);
        }
    }
}
