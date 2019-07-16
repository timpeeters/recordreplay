package be.xplore.recordreplay.service;

@FunctionalInterface
public interface Mapper<R, P> {
    R map(P p);
}
