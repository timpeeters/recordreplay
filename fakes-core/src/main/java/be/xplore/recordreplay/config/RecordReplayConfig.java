package be.xplore.recordreplay.config;

import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestMatcher;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.UseCase;

import java.util.List;

import static be.xplore.fakes.util.Assert.notNull;

public class RecordReplayConfig implements Configuration {
    private String host;
    private int port;
    private UseCase useCase;
    private Repository repository;
    private HttpClient client;
    private List<RequestMatcher> matchers;

    public RecordReplayConfig() {
        host = DEFAULT_LISTEN_ADDRESS;
        port = DEFAULT_PORT;
    }

    public RecordReplayConfig host(String host) {
        this.host = notNull(host);
        return this;
    }

    @Override
    public String host() {
        return host;
    }

    public RecordReplayConfig port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public int port() {
        return port;
    }


    @Override
    public UseCase useCase() {
        return useCase;
    }

    public RecordReplayConfig repository(Repository repository) {
        this.repository = repository;
        return this;
    }

    @Override
    public Repository repository() {
        return repository;
    }

    public RecordReplayConfig client(HttpClient client) {
        this.client = client;
        return this;
    }

    @Override
    public HttpClient client() {
        return client;
    }

    public RecordReplayConfig matchers(List<RequestMatcher> matchers) {
        this.matchers = matchers;
        return this;
    }

    @Override
    public List<RequestMatcher> matchers() {
        return matchers;
    }

    public RecordReplayConfig record() {
        this.useCase = createRecordUseCase();
        return this;
    }

    public RecordReplayConfig replay() {
        this.useCase = createReplayUseCase();
        return this;
    }

    public RecordReplayConfig recordReplay() {
        this.useCase = new RecordReplayUseCase(createRecordUseCase(), createReplayUseCase());
        return this;
    }

    private RecordUseCase createRecordUseCase() {
        return new RecordUseCase(repository(), client());
    }

    private ReplayUseCase createReplayUseCase() {
        return new ReplayUseCase(repository(), matchers());
    }
}
