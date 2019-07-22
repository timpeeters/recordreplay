package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class AbstractHttpServlet extends HttpServlet {
    private static final long serialVersionUID = 7825358410575851102L;

    private final HttpServletRequestMapper requestMapper;
    private final ResponseMapper<HttpServletResponse> responseMapper;

    AbstractHttpServlet() {
        super();
        requestMapper = new HttpServletRequestMapper();
        this.responseMapper = new HttpServletResponseMapper();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Stub stub = new Stub(requestMapper.map(req), null);
        Optional<Response> response = executeUseCase(stub);
        response.ifPresentOrElse(value -> responseMapper.map(value, resp), () -> {
            throw new NoSuchElementException(String
                    .format("No response available for request: %s", stub.getRequest().toString()));
        });
    }

    protected abstract Optional<Response> executeUseCase(Stub stub);
}
