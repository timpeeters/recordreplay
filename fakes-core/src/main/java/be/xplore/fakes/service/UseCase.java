package be.xplore.fakes.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;

import java.util.Optional;

interface UseCase {
    Optional<Response> execute(Stub stub);
}
