package be.xplore.recordreplay.service;

@FunctionalInterface
public interface Mapper<R, P> {
    void map(R r, P p);
}
