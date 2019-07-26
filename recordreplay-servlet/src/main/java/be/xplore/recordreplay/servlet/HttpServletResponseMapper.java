package be.xplore.recordreplay.servlet;

import be.xplore.fakes.model.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

class HttpServletResponseMapper {
    void map(Response response, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(response.getStatusCode());
        convertHeaders(response, httpServletResponse);
        convertBody(response, httpServletResponse);
    }

    private void convertHeaders(Response response, HttpServletResponse httpServletResponse) {
        String[] headers = response.getHeaders().toVarargs();
        for (int i = 0; i < headers.length; i += 2) {
            httpServletResponse.addHeader(headers[i], headers[i + 1]);
        }
    }

    private void convertBody(Response response, HttpServletResponse httpServletResponse) {
        try (Writer writer = httpServletResponse.getWriter()){
            writer.write(response.getBody());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
