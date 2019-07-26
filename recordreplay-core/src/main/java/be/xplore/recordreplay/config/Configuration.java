package be.xplore.recordreplay.config;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.service.Repository;
import be.xplore.recordreplay.service.RequestBodyMatcher;
import be.xplore.recordreplay.service.RequestHeaderMatcher;
import be.xplore.recordreplay.service.RequestMatcher;
import be.xplore.recordreplay.service.RequestMethodMatcher;
import be.xplore.recordreplay.service.RequestParamMatcher;
import be.xplore.recordreplay.service.RequestPathMatcher;

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
