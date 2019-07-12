package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;

import java.util.List;
import java.util.Map;

class YamlRequest {
    public RequestMethod method;
    public String path;
    public Map<String, List<String>> queryParams;
    public Map<String, List<String>> headers;
    public String body;

    public YamlRequest() {
    }

    public YamlRequest(Request request) {
        this.method = request.getMethod();
        this.path = request.getPath();
        this.queryParams = request.getQueryParams().getParams();
        this.headers = request.getHeaders().getHeaderMap();
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
