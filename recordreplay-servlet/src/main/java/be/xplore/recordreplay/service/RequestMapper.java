package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Request;

@FunctionalInterface
public interface RequestMapper<R> extends Mapper<Request, R> {
}
