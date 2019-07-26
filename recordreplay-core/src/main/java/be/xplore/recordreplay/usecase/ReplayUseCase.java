package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.Repository;
import be.xplore.recordreplay.service.RequestMatcher;

import java.util.List;
import java.util.Optional;

public class ReplayUseCase implements UseCase{

    private final Repository repository;
    private final List<RequestMatcher> matchers;

    public ReplayUseCase(Repository repository, List<RequestMatcher> matchers) {
        this.repository = repository;
        this.matchers = matchers;
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        return repository.findExactResponse(stub.getRequest(), matchers);
    }
}
