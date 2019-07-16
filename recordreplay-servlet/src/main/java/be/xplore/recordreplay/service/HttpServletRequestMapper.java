package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpServletRequestMapper implements RequestMapper<HttpServletRequest> {
    @Override
    public Request map(HttpServletRequest request) {
        return Request.builder()
                .method(convertMethod(request.getMethod()))
                .path(request.getRequestURL().toString())
                .queryParams(convertParams(request.getParameterMap()))
                .headers(convertHeaders(request))
                .build();
    }

    private RequestMethod convertMethod(String method) {
        return RequestMethod.valueOf(method);
    }

    private QueryParams convertParams(Map<String, String[]> params) {
        Map<String, List<String>> collect = params.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        stringEntry -> List.of(stringEntry.getValue())
                ));
        return QueryParams.builder().params(collect).build();
    }

    private Headers convertHeaders(HttpServletRequest request) {
        var builder = Headers.builder();
        request.getHeaderNames().asIterator().forEachRemaining(
                key -> request.getHeaders(key).asIterator().forEachRemaining(
                        val -> builder.header(key, val)
                )
        );
        return builder.build();
    }
}
