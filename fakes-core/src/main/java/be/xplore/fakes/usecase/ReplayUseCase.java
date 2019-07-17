package be.xplore.fakes.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestMatcher;

import java.util.List;
import java.util.Optional;

public class ReplayUseCase {

    private final Repository repository;

    public ReplayUseCase(Repository repository) {
        this.repository = repository;
    }

    public Optional<Response> replay(Request request, List<RequestMatcher> matchers) {
        return repository.findExactResponse(request, matchers);
    }
}
