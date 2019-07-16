package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractHttpServletResponseMapper implements ResponseMapper<HttpServletResponse> {
    @Override
    public abstract void map(Response response, HttpServletResponse httpServletResponse);
}
