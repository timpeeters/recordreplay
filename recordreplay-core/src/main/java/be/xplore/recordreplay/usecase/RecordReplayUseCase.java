package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.matcher.MatcherWrapper;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.Repository;

import java.util.Optional;

public class RecordReplayUseCase implements UseCase {

    private final RecordUseCase record;
    private final ReplayUseCase replay;

    public RecordReplayUseCase(Repository repository, HttpClient client, MatcherWrapper matcherWrapper) {
        this.record = new RecordUseCase(repository, client);
        this.replay = new ReplayUseCase(repository, matcherWrapper);
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

