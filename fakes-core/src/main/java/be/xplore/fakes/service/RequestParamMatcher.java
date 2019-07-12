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
        if (params.isEmpty() && otherParams.isEmpty()) {
            return 0;
        } else if (params.isEmpty() || otherParams.isEmpty()) {
            return 1;
        }
        return largest(params, otherParams)
                .returnMismatchingQueries(smallest(params, otherParams))
                .size() * (1D / largest(params, otherParams).size());
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
