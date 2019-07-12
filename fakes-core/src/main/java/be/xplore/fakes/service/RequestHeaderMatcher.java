package be.xplore.fakes.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

public class RequestHeaderMatcher implements RequestMatcher {

    @Override
    public Result matches(Request request, Request otherRequest) {
        return new Result(calculateDistance(request.getHeaders(), otherRequest.getHeaders()));
    }

    private double calculateDistance(Headers headers, Headers otherHeaders) {
        double distance;
        if (headers.isEmpty() && otherHeaders.isEmpty()) {
            distance = 0;
        } else if (headers.isEmpty() || otherHeaders.isEmpty()) {
            distance = 1;
        } else {
            distance = largest(headers, otherHeaders)
                .returnMismatchingHeaders(smallest(headers, otherHeaders))
                    .size() * (1D / largest(headers, otherHeaders).size());
        }
        return distance;
    }

    private Headers largest(Headers headers, Headers otherHeaders) {
        if (headers.size() > otherHeaders.size()) {
            return headers;
        } else {
            return otherHeaders;
        }
    }

    private Headers smallest(Headers headers, Headers otherHeaders) {
        if (headers.size() > otherHeaders.size()) {
            return otherHeaders;
        } else {
            return headers;
        }
    }
}
