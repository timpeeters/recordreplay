package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Result;
import be.xplore.recordreplay.model.Stub;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MatcherWrapper {

    private final List<RequestMatcher> matchers;

    public MatcherWrapper(List<RequestMatcher> matchers) {
        this.matchers = matchers;
    }

    public Optional<Response> getResponse(Request request, List<Stub> stubs) {
        try {
            return stubs
                    .stream()
                    .map(stub -> new Result(matchers
                            .stream()
                            .map(matcher -> matcher.matches(request, stub.getRequest()))
                            .mapToDouble(Result::getDistance)
                            .sum() / matchers.size(), stub))
                    .min(Comparator.comparing(Result::getDistance))
                    .map(rs -> rs.getStub().getResponse());
        } catch (NoExactMatchFoundException e) {
            return Optional.empty();
        }
    }

}
