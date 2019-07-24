package be.xplore.recordreplay.config;

import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestMatcher;
import be.xplore.recordreplay.usecase.UseCase;

import java.util.List;

public interface Configuration {

    int DEFAULT_PORT = 8080;

    String host();

    int port();

    UseCase useCase();

    Repository repository();

    HttpClient client();

    List<RequestMatcher> matchers();
}
