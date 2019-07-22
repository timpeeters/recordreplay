package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.*;
import be.xplore.usecases.ReplayUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReplayHttpServlet extends DefaultHttpServlet {

    private ReplayUseCase useCase;

    public ReplayHttpServlet() {
        List<RequestMatcher> matchers = new ArrayList<>();
        matchers.add(new RequestBodyMatcher());
        matchers.add(new RequestHeaderMatcher());
        matchers.add(new RequestMethodMatcher());
        matchers.add(new RequestParamMatcher());
        matchers.add(new RequestPathMatcher());
        this.useCase = new ReplayUseCase(new MemoryRepository(),matchers);
    }

    @Override
    protected Optional<Response> executeUseCase(Stub stub) {
        return useCase.execute(stub);
    }
}
