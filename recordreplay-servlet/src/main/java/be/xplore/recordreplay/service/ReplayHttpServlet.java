package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.MemoryRepository;
import be.xplore.fakes.service.RequestBodyMatcher;
import be.xplore.fakes.service.RequestHeaderMatcher;
import be.xplore.fakes.service.RequestMatcher;
import be.xplore.fakes.service.RequestMethodMatcher;
import be.xplore.fakes.service.RequestParamMatcher;
import be.xplore.fakes.service.RequestPathMatcher;
import be.xplore.usecases.ReplayUseCase;

import java.util.List;
import java.util.Optional;

public class ReplayHttpServlet extends AbstractHttpServlet {

    private static final long serialVersionUID = -1722918342394082882L;
    private final ReplayUseCase useCase;

    public ReplayHttpServlet() {
        super();
        List<RequestMatcher> matchers = List.of(
                new RequestBodyMatcher(),
                new RequestHeaderMatcher(),
                new RequestMethodMatcher(),
                new RequestParamMatcher(),
                new RequestPathMatcher()
        );
        this.useCase = new ReplayUseCase(new MemoryRepository(), matchers);
    }

    @Override
    protected Optional<Response> executeUseCase(Stub stub) {
        return useCase.execute(stub);
    }
}
