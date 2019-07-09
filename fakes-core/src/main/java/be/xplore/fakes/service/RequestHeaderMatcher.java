package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

import java.util.List;

public class RequestHeaderMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (validateHeaderLists(request, otherRequest)) {
            return new Result().setDistance(1);
        }
        return new Result().setDistance(calculateMatchDistance(request, otherRequest));
    }

    private boolean validateHeaderLists(Request request, Request otherRequest) {
        if (request.getHeaders() == null || otherRequest.getHeaders() == null) {
            return true;
        }
        if (request.getHeaders().size() == 0 || otherRequest.getHeaders().size() == 0) {
            return true;
        }
        return false;
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
        if (request.getHeaders().size() > otherRequest.getHeaders().size()) {
            return request.getHeaders();
        } else {
            return otherRequest.getHeaders();
        }
    }

    private List<String> determineSmallestList(Request request, Request otherRequest) {
        if (request.getHeaders().size() > otherRequest.getHeaders().size()) {
            return otherRequest.getHeaders();
        } else {
            return request.getHeaders();
        }
    }
}
