package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;

import java.util.Optional;

interface UseCase {
    Optional<Response> execute(Stub stub);
}
