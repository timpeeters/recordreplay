package be.xplore.recordreplay.repo;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.IterableUtil.toArray;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestMapperTest {

    private static final String PATH = "http://localhost:8080/user";

    private HttpServletRequestMapper mapper;
    private final Request.Builder requestBuilder = Request.Builder.get(PATH);
    private HttpServletRequest validHttpServletRequest;

    @Before
    public void setupMapper() {
        mapper = new HttpServletRequestMapper();
    }

    @Before
    public void setupMock() throws MalformedURLException {
        validHttpServletRequest = mock(HttpServletRequest.class);
        when(validHttpServletRequest.getMethod()).thenReturn("GET");
        when(validHttpServletRequest.getRequestURL())
                .thenReturn(new StringBuffer(URI.create(PATH).toURL().toString()));
    }

    @Test
    public void convertMethod() {
        when(validHttpServletRequest.getMethod()).thenReturn("GET");
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request.getMethod())
                .isEqualTo(requestBuilder.method(RequestMethod.GET).build().getMethod());

    }

    @Test
    public void methodCaseInsensitive() {
        when(validHttpServletRequest.getMethod()).thenReturn("tRacE");
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request.getMethod()).isEqualTo(requestBuilder.method(RequestMethod.TRACE).build().getMethod());
    }

    @Test
    public void convertUrl() throws MalformedURLException {
        when(validHttpServletRequest.getRequestURL())
                .thenReturn(new StringBuffer(URI.create(PATH).toURL().toString()));
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request.getPath()).isEqualTo(requestBuilder.build().getPath());
    }

    @Test
    public void convertHeaders() {
        when(validHttpServletRequest.getHeaderNames())
                .thenReturn(Collections.enumeration(singletonList("Content-Type")));
        when(validHttpServletRequest.getHeaders(same("Content-Type")))
                .thenReturn(Collections.enumeration(singletonList("application/json")));
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request.getHeaders()).isEqualTo(
                requestBuilder
                        .headers(Headers.builder().header("Content-Type", "application/json").build())
                        .build()
                        .getHeaders()
        );
    }

    @Test
    public void convertParams() {
        String idKey = "id";
        List<String> idValues = List.of("1", "2", "10");
        when(validHttpServletRequest.getParameterMap())
                .thenReturn(Map.of(idKey, toArray(idValues, String.class)));
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request.getQueryParams()).isEqualTo(
                requestBuilder
                        .queryParams(QueryParams.builder().params(Map.of(idKey, idValues))
                                .build())
                        .build()
                        .getQueryParams()
        );
    }

    @Test
    public void convertBody() throws IOException {
        String body = "body" + System.lineSeparator() + "nextLine";
        when(validHttpServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(body)));
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request.getBody()).isEqualTo(body);
    }

}
