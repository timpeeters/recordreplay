package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.recordreplay.repo.HttpClient;
import be.xplore.recordreplay.repo.Repository;
import be.xplore.recordreplay.repo.RequestMatcher;

import java.util.List;
import java.util.Optional;

public class RecordReplayUseCase implements UseCase {

    private final RecordUseCase record;
    private final ReplayUseCase replay;

    public RecordReplayUseCase(Repository repository, HttpClient client, List<RequestMatcher> matchers) {
        this.record = new RecordUseCase(repository, client);
        this.replay = new ReplayUseCase(repository, matchers);
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        Optional<Response> foundResp = replay.execute(stub);
        if (foundResp.isPresent()) {
            return foundResp;
        } else {
            return record.execute(stub);
        }
    }
}

