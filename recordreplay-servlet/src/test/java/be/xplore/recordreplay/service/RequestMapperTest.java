package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestMapperTest {

    private Mapper<Request, HttpServletRequest> mapper;

    private HttpServletRequest validHttpServletRequest;

    @Before
    public void setupMapper() {
        mapper = new HttpServletRequestMapper();
    }

    @Before
    public void setupMock() throws MalformedURLException {
        validHttpServletRequest = mock(HttpServletRequest.class);
        when(validHttpServletRequest.getMethod())
                .thenReturn("GET");
        when(validHttpServletRequest.getHeaderNames())
                .thenReturn(Collections.enumeration(singletonList("Content-Type")));
        when(validHttpServletRequest.getHeaders("Content-Type"))
                .thenReturn(Collections.enumeration(singletonList("application/json")));
        when(validHttpServletRequest.getRequestURL())
                .thenReturn(new StringBuffer(URI.create("http://localhost:8080/user").toURL().toString()));
    }

    @Test
    public void httpServletRequestMapper() {
        Request request = mapper.map(validHttpServletRequest);
        assertThat(request).isEqualTo(Request.Builder
                .get("http://localhost:8080/user")
                .headers(Headers.builder().header("Content-Type", "application/json").build())
                .build()
        );
    }
}
