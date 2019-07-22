package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.MemoryRepository;
import be.xplore.fakes.service.Repository;
import be.xplore.fakes.service.RequestBodyMatcher;
import be.xplore.fakes.service.RequestHeaderMatcher;
import be.xplore.fakes.service.RequestMatcher;
import be.xplore.fakes.service.RequestMethodMatcher;
import be.xplore.fakes.service.RequestParamMatcher;
import be.xplore.fakes.service.RequestPathMatcher;
import be.xplore.usecases.ForwardRequestUseCase;
import be.xplore.usecases.RecordReplayUseCase;
import be.xplore.usecases.RecordUseCase;
import be.xplore.usecases.ReplayUseCase;

import java.util.List;
import java.util.Optional;

public class RecordReplayHttpServlet extends AbstractHttpServlet {

    private static final long serialVersionUID = 4496621486970431156L;
    private final RecordReplayUseCase useCase;

    public RecordReplayHttpServlet() {
        super();
        Repository repo = new MemoryRepository();
        List<RequestMatcher> matchers = initMatchers();
        this.useCase = new RecordReplayUseCase(new ReplayUseCase(repo, matchers),
                new ForwardRequestUseCase(new OkHttpClient()),
                new RecordUseCase(repo));
    }

    @Override
    protected Optional<Response> executeUseCase(Stub stub) {
        return useCase.execute(stub);
    }

    private List<RequestMatcher> initMatchers() {
        return List.of(
                new RequestBodyMatcher(),
                new RequestHeaderMatcher(),
                new RequestMethodMatcher(),
                new RequestParamMatcher(),
                new RequestPathMatcher()
        );
    }

}
