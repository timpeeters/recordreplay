package be.xplore.recordreplay.config;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.matcher.MatchFinder;
import be.xplore.recordreplay.matcher.RequestMatcher;
import be.xplore.recordreplay.repository.MemoryRepository;
import be.xplore.recordreplay.repository.Repository;
import be.xplore.recordreplay.util.ClassLocator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static be.xplore.recordreplay.util.Assert.notNull;

public class RecordReplayConfig implements Configuration {
    private String host;
    private int port;
    private HttpClient httpClient;
    private Repository repository;
    private MatchFinder matchFinder;
    private URL target;

    public RecordReplayConfig() {
        this.host = DEFAULT_LISTEN_ADDRESS;
        this.port = DEFAULT_PORT;
        this.httpClient = new ClassLocator<>(HttpClient.class).load();
        this.matchFinder = DEFAULT_MATCHERS;
        this.repository = new MemoryRepository();
        target("");
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


    public RecordReplayConfig repository(Repository repository) {
        this.repository = repository;
        return this;
    }

    @Override
    public Repository repository() {
        return repository;
    }

    public RecordReplayConfig client(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    @Override
    public HttpClient client() {
        return httpClient;
    }

    public RecordReplayConfig matchers(List<RequestMatcher> matchers) {
        if (matchers == null || matchers.isEmpty()) {
            throw new IllegalArgumentException(
                    "Using RecordReplay without matchers will result in undefined behaviour!"
            );
        }
        this.matchFinder = new MatchFinder(matchers);
        return this;
    }

    @Override
    public MatchFinder matchers() {
        return matchFinder;
    }

    @SuppressWarnings("PMD.NullAssignment")
    public RecordReplayConfig target(String target) {
        if (target == null || target.isEmpty()) {
            this.target = null;
        } else {
            try {
                this.target = URI.create(target).toURL();
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return this;
    }

    @Override
    public URL target() {
        return this.target;
    }
}
