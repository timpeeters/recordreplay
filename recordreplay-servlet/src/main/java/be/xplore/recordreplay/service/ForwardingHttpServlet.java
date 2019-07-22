package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.usecases.ForwardRequestUseCase;

import java.util.Optional;

public class ForwardingHttpServlet extends AbstractHttpServlet {

    private static final long serialVersionUID = 2145397031115740791L;

    private final ForwardRequestUseCase useCase;

    public ForwardingHttpServlet() {
        super();
        useCase = new ForwardRequestUseCase(new OkHttpClient());
    }

    @Override
    protected Optional<Response> executeUseCase(Stub stub) {
        return Optional.of(useCase.execute(stub));
    }
}
