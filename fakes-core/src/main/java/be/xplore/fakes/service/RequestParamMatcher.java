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
        return request.getParams() == null
                || otherRequest.getParams() == null
                || request.getParams().size() == 0
                || otherRequest.getParams().size() == 0;
    }

    private double calculateMatchDistance(Request request, Request otherRequest) {
        List<String> smallestList = determineSmallestList(request, otherRequest);
        double distanceIncrement = getDistanceIncrement(request, otherRequest);
        return determineLargestList(request, otherRequest)
                .stream()
                .filter(s -> !smallestList.contains(s))
                .mapToDouble(s -> distanceIncrement)
                .sum();
    }

    private double getDistanceIncrement(Request request, Request otherRequest) {
        return 1D / determineLargestList(request, otherRequest).size();
    }

    private List<String> determineLargestList(Request request, Request otherRequest) {
        if (request.getParams().size() > otherRequest.getParams().size()) {
            return request.getParams();
        } else {
            return otherRequest.getParams();
        }
    }

    private List<String> determineSmallestList(Request request, Request otherRequest) {
        if (request.getParams().size() > otherRequest.getParams().size()) {
            return otherRequest.getParams();
        } else {
            return request.getParams();
        }
    }
}
