package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;
import be.xplore.recordreplay.util.StringDistance;

public class RequestPathMatcher implements RequestMatcher {

    private final boolean exactMatch;

    public RequestPathMatcher(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getPath().equals(otherRequest.getPath())) {
            return new Result(0);
        } else if (exactMatch) {
            throw new NoExactMatchFoundException("No match found for exact path-matcher");
        } else {
            return new Result(StringDistance.calculate(request.getPath(), otherRequest.getPath()));
        }
    }
}
