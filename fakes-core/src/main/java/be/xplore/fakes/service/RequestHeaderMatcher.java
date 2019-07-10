package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

import java.util.ArrayList;
import java.util.List;

public class RequestHeaderMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (validateHeaderMaps(request, otherRequest)) {
            return new Result().setDistance(1);
        }
        return new Result().setDistance(calculateMatchDistance(request, otherRequest));
    }

    private boolean validateHeaderMaps(Request request, Request otherRequest) {
        return request.getHeaders() == null
                || otherRequest.getHeaders() == null
                || request.getHeaders().size() == 0
                || otherRequest.getHeaders().size() == 0;
    }

    private double calculateMatchDistance(Request request, Request otherRequest) {
        List<String> smallestList = determineSmallestHeaderPairs(request, otherRequest);
        double distanceIncrement = getDistanceIncrement(request, otherRequest);
        return determineLargestHeaderPairs(request, otherRequest)
                .stream()
                .filter(s -> !smallestList.contains(s))
                .mapToDouble(s -> distanceIncrement)
                .sum();
    }

    private List<String> getKeyValueHeaderPairs(Request request) {
        List<String> keyValueHeaderPairs = new ArrayList<>();
        request.getHeaders()
                .forEach((headerKey, valueList) -> request.getHeaders().get(headerKey)
                        .forEach(headerValue -> keyValueHeaderPairs.add(headerKey + ":" + headerValue)));
        return keyValueHeaderPairs;
    }

    private double getDistanceIncrement(Request request, Request otherRequest) {
        return 1D / determineLargestHeaderPairs(request, otherRequest).size();
    }

    private List<String> determineLargestHeaderPairs(Request request, Request otherRequest) {
        List<String> keyValueHeaderPairs1 = getKeyValueHeaderPairs(request);
        List<String> keyValueHeaderPairs2 = getKeyValueHeaderPairs(otherRequest);
        if (keyValueHeaderPairs1.size() > keyValueHeaderPairs2.size()) {
            return keyValueHeaderPairs1;
        } else {
            return keyValueHeaderPairs2;
        }
    }

    private List<String> determineSmallestHeaderPairs(Request request, Request otherRequest) {
        List<String> keyValueHeaderPairs1 = getKeyValueHeaderPairs(request);
        List<String> keyValueHeaderPairs2 = getKeyValueHeaderPairs(otherRequest);
        if (keyValueHeaderPairs1.size() > keyValueHeaderPairs2.size()) {
            return keyValueHeaderPairs2;
        } else {
            return keyValueHeaderPairs1;
        }
    }
}
