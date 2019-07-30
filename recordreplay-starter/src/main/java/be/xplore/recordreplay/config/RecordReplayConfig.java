package be.xplore.recordreplay.config;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.http.OkHttpClient;
import be.xplore.recordreplay.matcher.RequestMatcher;
import be.xplore.recordreplay.repository.MemoryRepository;
import be.xplore.recordreplay.repository.Repository;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static be.xplore.recordreplay.util.Assert.notNull;

public class RecordReplayConfig implements Configuration {
    private String host;
    private int port;
    private Repository repository;
    private List<RequestMatcher> matchers;
    private URL target;

    public RecordReplayConfig() {
        this.host = DEFAULT_LISTEN_ADDRESS;
        this.port = DEFAULT_PORT;
        this.matchers = DEFAULT_MATCHERS;
        this.repository = new MemoryRepository();
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

    @Override
    public HttpClient client() {
        return OkHttpClient.noProxy();
    }

    public RecordReplayConfig matchers(List<RequestMatcher> matchers) {
        this.matchers = notNull(matchers);
        return this;
    }

    @Override
    public List<RequestMatcher> matchers() {
        return matchers;
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
