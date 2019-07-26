package be.xplore.recordreplay;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.UseCase;

public abstract class RecordReplay<T extends RecordReplay<T>> {
    private final Configuration configuration;

    protected RecordReplay(Configuration configuration) {
        this.configuration = configuration;
    }

    public T forward() {
        createRecordReplay(new ForwardRequestUseCase(getConfig().client()));
        return (T) this;
    }

    public T record() {
        createRecordReplay(new RecordUseCase(getConfig().repository(), getConfig().client()));
        return (T) this;
    }

    public T replay() {
        createRecordReplay(new ReplayUseCase(getConfig().repository(), getConfig().matchers()));
        return (T) this;
    }

    public T recordReplay() {
        createRecordReplay(new RecordReplayUseCase(getConfig().repository(), getConfig().client(), getConfig()
                .matchers()));
        return (T) this;
    }

    protected abstract void createRecordReplay(UseCase useCase);

    protected Configuration getConfig() {
        return configuration;
    }
}
