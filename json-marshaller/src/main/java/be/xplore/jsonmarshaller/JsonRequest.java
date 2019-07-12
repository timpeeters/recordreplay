package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;

import java.util.List;

class JsonRequest {
    public RequestMethod method;
    public String path;
    public List<String> params;
    public List<String> headers;
    public String body;

    Request toRequest() {
        return Request.builder().method(method).path(path).queryParams(params).headers(headers).body(body).build();
    }
}
