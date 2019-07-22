package be.xplore.usecases;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.util.Assert;

import java.util.Optional;

public class RecordReplayUseCase {

    private final ReplayUseCase replay;
    private final ForwardRequestUseCase forward;
    private final RecordUseCase record;

    public RecordReplayUseCase(ReplayUseCase replay, ForwardRequestUseCase forward, RecordUseCase record) {
        this.replay = Assert.notNull(replay);
        this.forward = Assert.notNull(forward);
        this.record = Assert.notNull(record);
    }

    public Optional<Response> execute(Stub stub) {
        Optional<Response> foundResp = replay.execute(stub);
        if (foundResp.isPresent()) {
            return foundResp;
        } else {
            return record.execute(new Stub(stub.getRequest(), forward.execute(stub)));
        }
    }
}

