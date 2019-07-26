package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.usecase.StubHandler;

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

    public RecordReplayHttpServlet(StubHandler stubHandler) {
        super();
        this.requestMapper = new HttpServletRequestMapper();
        this.responseMapper = new HttpServletResponseMapper();
        this.stubHandler = stubHandler;
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
