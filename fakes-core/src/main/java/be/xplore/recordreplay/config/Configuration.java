package be.xplore.recordreplay.config;

import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestBodyMatcher;
import be.xplore.fakes.service.RequestHeaderMatcher;
import be.xplore.fakes.service.RequestMatcher;
import be.xplore.fakes.service.RequestMethodMatcher;
import be.xplore.fakes.service.RequestParamMatcher;
import be.xplore.fakes.service.RequestPathMatcher;

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
