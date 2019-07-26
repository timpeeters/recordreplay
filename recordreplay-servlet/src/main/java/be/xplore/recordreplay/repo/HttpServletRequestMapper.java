package be.xplore.recordreplay.repo;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

class HttpServletRequestMapper {

    Request map(HttpServletRequest servletRequest) {
        return Request.builder().method(convertMethod(servletRequest.getMethod()))
                .path(servletRequest.getRequestURL().toString())
                .queryParams(convertParams(servletRequest.getParameterMap()))
                .headers(convertHeaders(servletRequest))
                .body(tryReadBody(servletRequest))
                .build();
    }

    private String tryReadBody(HttpServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            if (reader != null) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return "";
    }

    private RequestMethod convertMethod(String method) {
        return RequestMethod.valueOf(method.toUpperCase(Locale.ENGLISH));
    }

    private QueryParams convertParams(Map<String, String[]> params) {
        Map<String, List<String>> collect = params.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        stringEntry -> List.of(stringEntry.getValue())
                ));
        return QueryParams.builder().params(collect).build();
    }

    private Headers convertHeaders(HttpServletRequest servletRequest) {
        if (servletRequest.getHeaderNames() == null) {
            return Headers.EMPTY;
        }
        var builder = Headers.builder();
        servletRequest.getHeaderNames().asIterator().forEachRemaining(
                key -> servletRequest.getHeaders(key).asIterator().forEachRemaining(
                        val -> builder.header(key, val)
                )
        );
        return builder.build();
    }
}
