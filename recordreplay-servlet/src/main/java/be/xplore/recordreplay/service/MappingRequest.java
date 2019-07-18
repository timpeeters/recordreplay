package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;

public class MappingRequest {
    private final Request.Builder builder;

    public MappingRequest() {
        builder = Request.builder();
    }

    void setMethod(RequestMethod method){
        builder.method(method);
    }

    void setPath(String path) {
        builder.path(path);
    }

    void setQueryParams(QueryParams queryParams) {
        builder.queryParams(queryParams);
    }

    void setHeaders(Headers headers) {
        builder.headers(headers);
    }

    void setBody(String body) {
        builder.body(body);
    }

    Request toRequest(){
        return builder.build();
    }
}
