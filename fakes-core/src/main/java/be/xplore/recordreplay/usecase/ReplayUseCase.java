package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestMatcher;

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
