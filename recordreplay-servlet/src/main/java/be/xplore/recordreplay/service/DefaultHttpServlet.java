package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultHttpServlet extends HttpServlet {
    private static final long serialVersionUID = 7825358410575851102L;

    private final HttpServletRequestMapper requestMapper;
    private final ResponseMapper<HttpServletResponse> responseMapper;
    private final HttpClient client;

    public DefaultHttpServlet(ResponseMapper<HttpServletResponse> responseMapper) {
        super();
        requestMapper = new HttpServletRequestMapper();
        this.responseMapper = responseMapper;
        client = new DefaultHttpClient();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Request request = requestMapper.map(req);
        Response response = client.execute(request);
        responseMapper.map(response, resp);
    }
}
