package be.xplore.recordreplay.config;

import be.xplore.recordreplay.usecase.UseCase;

public interface Configuration {
    String host();

    int port();

    UseCase useCase();
}
