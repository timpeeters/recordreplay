package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Result;

@FunctionalInterface
public interface RequestMatcher {

    Result matches(Request request, Request otherRequest);

}
