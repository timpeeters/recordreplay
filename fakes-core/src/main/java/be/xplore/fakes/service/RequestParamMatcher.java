package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

import java.util.List;

public class RequestParamMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (validateParamLists(request, otherRequest)) {
            return new Result().setDistance(1);
        }
        return new Result().setDistance(calculateMatchDistance(request, otherRequest));
    }

    private boolean validateParamLists(Request request, Request otherRequest) {
        if (request.getParams() == null || otherRequest.getParams() == null) {
            return true;
        }
        if (request.getParams().size() == 0 || otherRequest.getParams().size() == 0) {
            return true;
        }
        return false;
    }

    private double calculateMatchDistance(Request request, Request otherRequest) {
        double distanceIncrement = getDistanceIncrement(request.getParams(), otherRequest.getParams());
        return request
                .getParams()
                .stream()
                .filter(s -> !otherRequest.getParams().contains(s))
                .mapToDouble(s -> distanceIncrement)
                .sum();
    }

    private double getDistanceIncrement(List<String> params1, List<String> params2) {
        return 1D / determineLargestList(params1, params2);
    }

    private int determineLargestList(List<String> params1, List<String> params2) {
        if (params1.size() > params2.size()) {
            return params1.size();
        } else {
            return params2.size();
        }
    }

}
