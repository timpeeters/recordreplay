package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.QueryParams;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestParamMatcher implements RequestMatcher {

    private final List<String> paramsToIgnore = new ArrayList<>();
    private final boolean exactMatch;

    public RequestParamMatcher(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public RequestParamMatcher(List<String> paramsToIgnore, boolean exactMatch) {
        this(exactMatch);
        this.paramsToIgnore.addAll(paramsToIgnore);
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        Result result = calculateDistance(request.getQueryParams(), otherRequest.getQueryParams());
        if(exactMatch && result.getDistance()!=0) {
            throw new NoExactMatchFoundException("No match found for exact param-matcher");
        } else {
            return result;
        }
    }

    private Result calculateDistance(QueryParams params, QueryParams otherParams) {
        Result result;
        if (params.isEmpty() && otherParams.isEmpty()) {
            result = new Result(0);
        } else if (params.isEmpty() || otherParams.isEmpty()) {
            result = new Result(1);
        } else {
            result = distanceAfterIgnoreParams(largest(params, otherParams), smallest(params, otherParams));
        }
        return result;
    }

    private Result distanceAfterIgnoreParams(QueryParams largest, QueryParams smallest) {
        Map<String, List<String>> largestMap = largest.getModifiableParamMap();
        Map<String, List<String>> smallestMap = smallest.getModifiableParamMap();
        largestMap.entrySet().removeIf(stringListEntry -> paramsToIgnore.stream()
                .anyMatch(s -> stringListEntry.getKey().equalsIgnoreCase(s)));
        smallestMap.entrySet().removeIf(stringListEntry -> paramsToIgnore.stream()
                .anyMatch(s -> stringListEntry.getKey().equalsIgnoreCase(s)));
        return new Result(QueryParams.builder().params(largestMap).build()
                .returnMismatchingQueries(QueryParams.builder().params(smallestMap).build())
                .size() * (1D / QueryParams.builder().params(largestMap).build().size()));
    }

    private QueryParams largest(QueryParams params, QueryParams otherParams) {
        if (params.size() > otherParams.size()) {
            return params;
        } else {
            return otherParams;
        }
    }

    private QueryParams smallest(QueryParams params, QueryParams otherParams) {
        if (params.size() > otherParams.size()) {
            return otherParams;
        } else {
            return params;
        }
    }
}
