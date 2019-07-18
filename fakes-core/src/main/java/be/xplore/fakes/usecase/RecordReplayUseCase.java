package be.xplore.fakes.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;

import java.util.Optional;

import static be.xplore.fakes.util.Assert.notNull;

public class RecordReplayUseCase {

    private final ReplayUseCase replay;
    private final ForwardRequestUseCase forward;
    private final RecordUseCase record;

    public RecordReplayUseCase(ReplayUseCase replay, ForwardRequestUseCase forward, RecordUseCase record) {
        this.replay = notNull(replay);
        this.forward = notNull(forward);
        this.record = notNull(record);
    }

    public Response execute(Request request) {
        Optional<Response> foundResp = replay.replay(request);
        return foundResp.orElseGet(() -> record.record(request, forward.execute(request)).getResponse());
    }

}
