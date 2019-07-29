package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

import static be.xplore.recordreplay.util.StringDistance.computeLevenshteinDistance;

public class RequestBodyMatcher  implements RequestMatcher{

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (request.getBody().equals(otherRequest.getBody())) {
            return new Result(0);
        } else {
            return new Result(computeLevenshteinDistance(request.getBody(), otherRequest.getBody()));
        }
    }
}
