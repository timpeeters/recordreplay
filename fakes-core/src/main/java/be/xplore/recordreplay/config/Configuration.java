package be.xplore.recordreplay.config;

import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestMatcher;

import java.util.List;

public interface Configuration {

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    String DEFAULT_LISTEN_ADDRESS = "0.0.0.0";
    int DEFAULT_PORT = 8080;

    String host();

    int port();

    Repository repository();

    HttpClient client();

    List<RequestMatcher> matchers();
}
