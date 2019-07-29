package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;
import be.xplore.recordreplay.util.StringDistance;

public class RequestBodyMatcher implements RequestMatcher {

    private final boolean exactMatch;

    public RequestBodyMatcher(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getBody().equals(otherRequest.getBody())) {
            return new Result(0);
        } else if (exactMatch) {
            throw new NoExactMatchFoundException("No match found for an exact body-matcher");
        } else {
            return new Result(StringDistance.calculate(request.getBody(), otherRequest.getBody()));
        }
    }
}
