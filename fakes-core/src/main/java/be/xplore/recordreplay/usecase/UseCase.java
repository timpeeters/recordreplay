package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;

import java.util.Optional;

public interface UseCase {
    Optional<Response> execute(Stub stub);
}
