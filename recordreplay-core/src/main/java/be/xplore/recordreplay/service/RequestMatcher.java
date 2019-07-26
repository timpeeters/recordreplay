package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Result;

@FunctionalInterface
public interface RequestMatcher {
    Result matches(Request request, Request otherRequest);
}
