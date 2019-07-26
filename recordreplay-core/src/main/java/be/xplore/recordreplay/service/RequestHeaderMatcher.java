package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestHeaderMatcher implements RequestMatcher {

    private final List<String> headersToIgnore = new ArrayList<>();

    public RequestHeaderMatcher() {
        this.headersToIgnore.addAll(List.of("User-Agent", "Connection", "Date"));
    }

    public RequestHeaderMatcher(List<String> headersToIgnore) {
        this.headersToIgnore.addAll(List.of("User-Agent", "Connection", "Date"));
        this.headersToIgnore.addAll(headersToIgnore);
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        return calculateDistance(request.getHeaders(), otherRequest.getHeaders());
    }

    private Result calculateDistance(Headers headers, Headers otherHeaders) {
        Result result;
        if (headers.isEmpty() && otherHeaders.isEmpty()) {
            result = new Result(0);
        } else if (headers.isEmpty() || otherHeaders.isEmpty()) {
            result = new Result(1);
        } else {
            result = distanceAfterIgnoreHeaders(
                    largest(headers, otherHeaders),
                    smallest(headers, otherHeaders));
        }
        return result;
    }

    private Result distanceAfterIgnoreHeaders(Headers largest, Headers smallest) {
        Map<String, List<String>> largestMap = largest.getModifiableHeaderMap();
        Map<String, List<String>> smallestMap = smallest.getModifiableHeaderMap();
        headersToIgnore.forEach(largestMap::remove);
        headersToIgnore.forEach(smallestMap::remove);
        return new Result(Headers.builder().headerMap(largestMap).build()
                .returnMismatchingHeaders(Headers.builder().headerMap(smallestMap).build())
                .size() * (1D / Headers.builder().headerMap(largestMap).build().size()));
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
