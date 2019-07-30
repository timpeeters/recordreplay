package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.matcher.MatchFinder;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.Repository;

import java.util.Optional;

public class ReplayUseCase implements UseCase{

    private final Repository repository;
    private final MatchFinder matchFinder;

    public ReplayUseCase(Repository repository, MatchFinder matchFinder) {
        this.repository = repository;
        this.matchFinder = matchFinder;
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        return matchFinder.getResponse(stub.getRequest(), repository.find());
    }
}
