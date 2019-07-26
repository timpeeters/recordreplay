package be.xplore.recordreplay.config;

import be.xplore.recordreplay.repo.HttpClient;
import be.xplore.recordreplay.repo.Repository;
import be.xplore.recordreplay.repo.RequestBodyMatcher;
import be.xplore.recordreplay.repo.RequestHeaderMatcher;
import be.xplore.recordreplay.repo.RequestMatcher;
import be.xplore.recordreplay.repo.RequestMethodMatcher;
import be.xplore.recordreplay.repo.RequestParamMatcher;
import be.xplore.recordreplay.repo.RequestPathMatcher;

import java.util.List;

public interface Configuration {

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    String DEFAULT_LISTEN_ADDRESS = "0.0.0.0";
    int DEFAULT_PORT = 8080;
    List<RequestMatcher> DEFAULT_MATCHERS = List.of(
            new RequestMethodMatcher(),
            new RequestPathMatcher(),
            new RequestHeaderMatcher(),
            new RequestParamMatcher(),
            new RequestBodyMatcher());

    String host();

    int port();

    Repository repository();

    HttpClient client();

    List<RequestMatcher> matchers();
}
