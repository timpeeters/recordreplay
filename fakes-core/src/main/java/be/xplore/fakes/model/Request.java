package be.xplore.fakes.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static be.xplore.fakes.util.Assert.notNull;

public class Request {

    private RequestMethod method;
    private String path;
    private List<String> params;
    private List<String> headers;
    private String body;

    public Request(Builder builder) {
        this.method = notNull(builder.method);
        this.path = notNull(builder.path);
        this.params = builder.params;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public RequestMethod getMethod() {
        return method;
    }

    public Request setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Request setPath(String path) {
        this.path = path;
        return this;
    }

    public List<String> getParams() {
        return params;
    }

    public Request setParams(List<String> params) {
        this.params = params;
        return this;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public Request setHeaders(List<String> headers) {
        this.headers = headers;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Request setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return this.method == request.getMethod() &&
                this.path.equals(request.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }


    public static class Builder {
        private RequestMethod method;
        private String path;
        private List<String> params;
        private List<String> headers;
        private String body;

        private Builder() {
            params = Collections.emptyList();
            headers = Collections.emptyList();
            body = "";
        }

        public Builder method(RequestMethod method) {
            this.method = method;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder params(List<String> params) {
            this.params = params;
            return this;
        }

        public Builder headers(List<String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
