package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.*;
import be.xplore.usecases.ForwardRequestUseCase;
import be.xplore.usecases.RecordReplayUseCase;
import be.xplore.usecases.RecordUseCase;
import be.xplore.usecases.ReplayUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordReplayHttpServlet extends DefaultHttpServlet {

    private RecordReplayUseCase useCase;

    public RecordReplayHttpServlet() {
        HttpClient httpClient = new OkHttpClient();
        Repository repo = new MemoryRepository();
        List<RequestMatcher> matchers = initMatchers();
        this.useCase = new RecordReplayUseCase(new ReplayUseCase(repo, matchers),
                new ForwardRequestUseCase(httpClient),
                new RecordUseCase(repo));
    }

    @Override
    protected Optional<Response> executeUseCase(Stub stub) {
        return useCase.execute(stub);
    }

    private List<RequestMatcher> initMatchers() {
        List<RequestMatcher> matchers = new ArrayList<>();
        matchers.add(new RequestBodyMatcher());
        matchers.add(new RequestHeaderMatcher());
        matchers.add(new RequestMethodMatcher());
        matchers.add(new RequestParamMatcher());
        matchers.add(new RequestPathMatcher());
        return matchers;
    }

}
