package be.xplore.recordreplay.repository;

import be.xplore.recordreplay.matcher.RequestMatcher;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Result;
import be.xplore.recordreplay.model.Stub;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface Repository {
    void add(Stub stub);

    List<Stub> find();

    default Optional<Response> findExactResponse(Request request, List<RequestMatcher> matchers) {
        Optional<Result> result = findBestMatch(request, matchers);
        if (result.isPresent() && result.get().getDistance() == 0) {
            return Optional.of(result.get().getStub().getResponse());
        }
        return Optional.empty();
    }

    default Optional<Result> findBestMatch(Request request, List<RequestMatcher> matchers) {
        return find()
                .stream()
                .map(stub -> new Result(matchers
                        .stream()
                        .map(matcher -> matcher.matches(request, stub.getRequest()))
                        .mapToDouble(Result::getDistance)
                        .sum() / matchers.size(), stub))
                .min(Comparator.comparing(Result::getDistance));
    }

}
