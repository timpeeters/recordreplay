package be.xplore.recordreplay.service;

@FunctionalInterface
public interface RequestMapper<R> extends Mapper<R, MappingRequest> {
}
