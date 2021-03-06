package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestHeaderMatcher implements RequestMatcher {

    private final List<String> headersToIgnore = new ArrayList<>();
    private final boolean exactMatch;

    public RequestHeaderMatcher(boolean exactMatch) {
        this.headersToIgnore.addAll(List.of("User-Agent", "Connection", "Date"));
        this.exactMatch = exactMatch;
    }

    public RequestHeaderMatcher(List<String> headersToIgnore, boolean exactMatch) {
        this(exactMatch);
        this.headersToIgnore.addAll(headersToIgnore);
    }

    @Override
    public Result matches(Request request, Request otherRequest) {
        Result result = calculateDistance(request.getHeaders(), otherRequest.getHeaders());
        if (exactMatch && result.getDistance() != 0) {
            throw new NoExactMatchFoundException("No match found for exact header-matcher");
        } else {
            return result;
        }
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
        largestMap.entrySet().removeIf(stringListEntry -> headersToIgnore.stream()
                .anyMatch(s -> stringListEntry.getKey().equalsIgnoreCase(s)));
        smallestMap.entrySet().removeIf(stringListEntry -> headersToIgnore.stream()
                .anyMatch(s -> stringListEntry.getKey().equalsIgnoreCase(s)));
        return new Result(getDistance(largestMap, smallestMap));
    }

    private double getDistance(Map<String, List<String>> largestMap, Map<String, List<String>> smallestMap) {
        return Headers.builder().headerMap(largestMap).build()
                .returnMismatchingHeaders(Headers.builder().headerMap(smallestMap).build())
                .size() * (1D / Headers.builder().headerMap(largestMap).build().size());
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
