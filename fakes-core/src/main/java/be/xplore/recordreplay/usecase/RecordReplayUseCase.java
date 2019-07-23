package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.util.Assert;

import java.util.Optional;

public class RecordReplayUseCase implements UseCase {

    private final RecordUseCase record;
    private final ReplayUseCase replay;

    public RecordReplayUseCase(RecordUseCase record, ReplayUseCase replay) {
        this.record = Assert.notNull(record);
        this.replay = Assert.notNull(replay);
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

