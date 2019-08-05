package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.matcher.stringsimilarity.NormalizedLevenshtein;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

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
            return new Result(new NormalizedLevenshtein().distance(request.getBody(), otherRequest.getBody()));
        }
    }
}
