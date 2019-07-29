package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

public class RequestBodyMatcher  implements RequestMatcher{

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getBody().equals(otherRequest.getBody())) {
            return new Result(0);
        } else {
            return new Result(1);
        }
    }
}
