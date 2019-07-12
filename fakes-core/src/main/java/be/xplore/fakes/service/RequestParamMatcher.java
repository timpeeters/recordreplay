package be.xplore.fakes.service;

import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

public class RequestParamMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        return new Result(calculateDistance(request.getQueryParams(), otherRequest.getQueryParams()));
    }

    private double calculateDistance(QueryParams params, QueryParams otherParams) {
        double distance;
        if (params.isEmpty() && otherParams.isEmpty()) {
            distance = 0;
        } else if (params.isEmpty() || otherParams.isEmpty()) {
            distance = 1;
        } else {
            distance = largest(params, otherParams)
                    .returnMismatchingQueries(smallest(params, otherParams))
                    .size() * (1D / largest(params, otherParams).size());
        }
        return distance;
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
