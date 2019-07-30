package be.xplore.recordreplay.config;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.matcher.MatchFinder;
import be.xplore.recordreplay.matcher.RequestBodyMatcher;
import be.xplore.recordreplay.matcher.RequestHeaderMatcher;
import be.xplore.recordreplay.matcher.RequestMethodMatcher;
import be.xplore.recordreplay.matcher.RequestParamMatcher;
import be.xplore.recordreplay.matcher.RequestPathMatcher;
import be.xplore.recordreplay.repository.Repository;

import java.net.URL;
import java.util.List;

public interface Configuration {

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    String DEFAULT_LISTEN_ADDRESS = "0.0.0.0";
    int DEFAULT_PORT = 8080;
    MatchFinder DEFAULT_MATCHERS = new MatchFinder(List.of(
            new RequestMethodMatcher(false),
            new RequestPathMatcher(false),
            new RequestHeaderMatcher(false),
            new RequestParamMatcher(false),
            new RequestBodyMatcher(false)));

    String host();

    int port();

    Repository repository();

    HttpClient client();

    MatchFinder matchers();

    URL target();
}
