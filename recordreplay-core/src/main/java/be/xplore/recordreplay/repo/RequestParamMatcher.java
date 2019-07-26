package be.xplore.recordreplay.repo;

import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestParamMatcher implements RequestMatcher {

    private final List<String> paramsToIgnore = new ArrayList<>();

    public RequestParamMatcher() {
    }

    public RequestParamMatcher(List<String> paramsToIgnore) {
        this.paramsToIgnore.addAll(paramsToIgnore);
    }


    @Override
    public Result matches(Request request, Request otherRequest) {
        return calculateDistance(request.getQueryParams(), otherRequest.getQueryParams());
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
        paramsToIgnore.forEach(largestMap::remove);
        paramsToIgnore.forEach(smallestMap::remove);
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
