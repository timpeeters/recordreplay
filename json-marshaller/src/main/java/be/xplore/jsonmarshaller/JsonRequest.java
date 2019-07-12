package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;

class JsonRequest {
    public RequestMethod method;
    public String path;
    public JsonQueryParams params;
    public JsonHeaders headers;
    public String body;

    Request toRequest() {
        return Request.builder().method(method).path(path).queryParams(params.toQueryParams()).headers(headers.toHeaders()).body(body).build();
    }
}
