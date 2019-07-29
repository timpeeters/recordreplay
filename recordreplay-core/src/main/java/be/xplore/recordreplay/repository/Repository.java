package be.xplore.recordreplay.repository;

import be.xplore.recordreplay.matcher.NoExactMatchFoundException;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Result;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.matcher.RequestMatcher;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface Repository {
    void add(Stub stub);

    List<Stub> find();

    default Optional<Response> findExactResponse(Request request, List<RequestMatcher> matchers) {
        try {
            return find()
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
