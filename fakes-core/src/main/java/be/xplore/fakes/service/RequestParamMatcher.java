package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

import java.util.ArrayList;
import java.util.List;

public class RequestParamMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        if (validateParamMaps(request, otherRequest)) {
            return new Result().setDistance(1);
        }
        return new Result().setDistance(calculateMatchDistance(request, otherRequest));
    }

    private boolean validateParamMaps(Request request, Request otherRequest) {
        return request.getParams() == null
                || otherRequest.getParams() == null
                || request.getParams().size() == 0
                || otherRequest.getParams().size() == 0;
    }

    private double calculateMatchDistance(Request request, Request otherRequest) {
        List<String> smallestList = determineSmallestParamPairs(request, otherRequest);
        double distanceIncrement = getDistanceIncrement(request, otherRequest);
        return determineLargestParamPairs(request, otherRequest)
                .stream()
                .filter(s -> !smallestList.contains(s))
                .mapToDouble(s -> distanceIncrement)
                .sum();
    }

    private List<String> getKeyValueParamPairs(Request request) {
        List<String> keyValueParamPairs = new ArrayList<>();
        request.getParams()
                .forEach((paramKey, valueList) -> request.getParams().get(paramKey)
                        .forEach(paramValue -> keyValueParamPairs.add(paramKey + ":" + paramValue)));
        return keyValueParamPairs;
    }

    private double getDistanceIncrement(Request request, Request otherRequest) {
        return 1D / determineLargestParamPairs(request, otherRequest).size();
    }

    private List<String> determineLargestParamPairs(Request request, Request otherRequest) {
        List<String> keyValueParamPairs1 = getKeyValueParamPairs(request);
        List<String> keyValueParamPairs2 = getKeyValueParamPairs(otherRequest);
        if (keyValueParamPairs1.size() > keyValueParamPairs2.size()) {
            return keyValueParamPairs1;
        } else {
            return keyValueParamPairs2;
        }
    }

    private List<String> determineSmallestParamPairs(Request request, Request otherRequest) {
        List<String> keyValueParamPairs1 = getKeyValueParamPairs(request);
        List<String> keyValueParamPairs2 = getKeyValueParamPairs(otherRequest);
        if (keyValueParamPairs1.size() > keyValueParamPairs2.size()) {
            return keyValueParamPairs2;
        } else {
            return keyValueParamPairs1;
        }
    }
}
