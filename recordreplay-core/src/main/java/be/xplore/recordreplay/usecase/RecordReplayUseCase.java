package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;

import java.util.Optional;

public class RecordReplayUseCase implements UseCase {

    private final RecordUseCase record;
    private final ReplayUseCase replay;

    public RecordReplayUseCase(Configuration config) {
        this.record = new RecordUseCase(config.repository(), config.client(), config.target());
        this.replay = new ReplayUseCase(config.repository(), config.matchers());
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

