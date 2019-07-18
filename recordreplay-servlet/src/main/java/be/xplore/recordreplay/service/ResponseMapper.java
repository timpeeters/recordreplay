package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;

@FunctionalInterface
public interface ResponseMapper<R> extends Mapper<Response, R> {
}
