package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

public class RequestMethodMatcher implements RequestMatcher {

    private final boolean exactMatch;

    public RequestMethodMatcher(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getMethod().equals(otherRequest.getMethod())) {
            return new Result(0);
        } else if (exactMatch) {
            throw new NoExactMatchFoundException("No match found for exact Method matcher");
        } else {
            return new Result(1);
        }
    }
}
