package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Result;
import be.xplore.fakes.model.Stub;

import java.util.List;
import java.util.Optional;

public interface Repository {
    void add(Stub stub);

    List<Stub> find();

    default Optional<Response> findExactResponse(Request request, List<RequestMatcher> matchers) {
        return find()
                .stream()
                .filter(stub -> matchers
                        .stream()
                        .map(matcher -> matcher.matches(request, stub.getRequest()))
                        .mapToDouble(Result::getDistance)
                        .sum() == 0)
                .map(Stub::getResponse)
                .findFirst();
    }
}
