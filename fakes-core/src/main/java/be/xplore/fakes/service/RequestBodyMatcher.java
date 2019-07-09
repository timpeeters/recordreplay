package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

public class RequestBodyMatcher  implements RequestMatcher{

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getBody().equals(otherRequest.getBody())) {
            return new Result().setDistance(0);
        } else {
            return new Result().setDistance(1);
        }
    }
}
