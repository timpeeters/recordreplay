package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.matcher.stringsimilarity.NormalizedLevenshtein;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

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
            return new Result(new NormalizedLevenshtein().distance(request.getPath(), otherRequest.getPath()));
        }
    }
}
