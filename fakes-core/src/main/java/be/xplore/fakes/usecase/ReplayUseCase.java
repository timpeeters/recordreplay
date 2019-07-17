package be.xplore.fakes.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestMatcher;

import java.util.List;
import java.util.Optional;

public class ReplayUseCase {

    private final Repository repository;
    private final List<RequestMatcher> matchers;

    public ReplayUseCase(Repository repository, List<RequestMatcher> matchers) {
        this.repository = repository;
        this.matchers = matchers;
    }

    public Optional<Response> replay(Request request) {
        return repository.findExactResponse(request, matchers);
    }
}
