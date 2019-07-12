package be.xplore.fakes.model;

import java.net.URI;
import java.util.Objects;

import static be.xplore.fakes.util.Assert.notNull;

public class Request {

    private RequestMethod method;
    private String path;
    private QueryParams queryParams;
    private Headers headers;
    private String body;

    public Request(Builder builder) {
        this.method = notNull(builder.method);
        this.path = notNull(builder.path);
        this.queryParams = notNull(builder.queryParams);
        this.headers = notNull(builder.headers);
        this.body = notNull(builder.body);
    }

    public static Builder builder() {
        return new Builder();
    }

    public URI toUri() {
        return URI.create(getPath() + getQueryParams().getQueryString());
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
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
        private QueryParams queryParams;
        private Headers headers;
        private String body;

        private Builder() {
            queryParams = QueryParams.EMPTY;
            headers = Headers.EMPTY;
            body = "";
        }

        public static Builder get(String path) {
            return new Builder().method(RequestMethod.GET).path(path);
        }

        public static Builder post(String path) {
            return new Builder().method(RequestMethod.POST).path(path);
        }

        public static Builder put(String path) {
            return new Builder().method(RequestMethod.PUT).path(path);
        }

        public Builder method(RequestMethod method) {
            this.method = method;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder queryParams(QueryParams queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public Builder headers(Headers headers) {
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
