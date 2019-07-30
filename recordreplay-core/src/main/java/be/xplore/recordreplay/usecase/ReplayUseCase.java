package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.matcher.MatcherWrapper;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.Repository;

import java.util.Optional;

public class ReplayUseCase implements UseCase{

    private final Repository repository;
    private final MatcherWrapper matcherWrapper;

    public ReplayUseCase(Repository repository, MatcherWrapper matcherWrapper) {
        this.repository = repository;
        this.matcherWrapper = matcherWrapper;
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        return matcherWrapper.getResponse(stub.getRequest(), repository.find());
    }
}
