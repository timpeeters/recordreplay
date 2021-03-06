package be.xplore.recordreplay.marshaller;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.QueryParams;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
class YamlRequest {
    public RequestMethod method;
    public String path;
    public Map<String, List<String>> queryParams;
    public Map<String, List<String>> headers;
    public String body;

    YamlRequest() {
    }

    YamlRequest(Request request) {
        this.method = request.getMethod();
        this.path = request.getPath();
        this.queryParams = request.getQueryParams().getModifiableQueryMap();
        this.headers = request.getHeaders().getModifiableHeaderMap();
        this.body = request.getBody();
    }


    Request toRequest() {
        return Request.builder()
                .method(method)
                .path(path)
                .queryParams(QueryParams.builder().params(queryParams).build())
                .headers(Headers.builder().headerMap(headers).build())
                .body(body)
                .build();
    }
}
