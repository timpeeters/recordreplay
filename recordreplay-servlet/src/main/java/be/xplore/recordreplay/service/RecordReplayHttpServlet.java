package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.StubHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RecordReplayHttpServlet extends HttpServlet {
    private static final long serialVersionUID = 7825358410575851102L;

    private final HttpServletRequestMapper requestMapper;
    private final HttpServletResponseMapper responseMapper;
    private final StubHandler stubHandler;

    RecordReplayHttpServlet() {
        super();
        this.requestMapper = new HttpServletRequestMapper();
        this.responseMapper = new HttpServletResponseMapper();
        stubHandler = StubHandler.getCurrent();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Stub stub = new Stub(requestMapper.map(req), null);
        Optional<Response> response = stubHandler.handle(stub);
        response.ifPresentOrElse(value -> responseMapper.map(value, resp), () -> {
            throw new NoSuchElementException(String
                    .format("No response available for request: %s", stub.getRequest().toString()));
        });
    }
}
