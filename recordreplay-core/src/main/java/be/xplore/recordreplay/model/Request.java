package be.xplore.recordreplay.model;

import java.net.URI;
import java.util.Objects;

import static be.xplore.recordreplay.util.Assert.notNull;

public class Request {

    private final RequestMethod method;
    private final String path;
    private final QueryParams queryParams;
    private final Headers headers;
    private final String body;

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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request otherRequest = (Request) o;
        return this.method == otherRequest.method &&
                Objects.equals(this.path, otherRequest.path) &&
                Objects.equals(this.queryParams, otherRequest.queryParams) &&
                Objects.equals(this.headers, otherRequest.headers) &&
                Objects.equals(this.body, otherRequest.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, queryParams, headers, body);
    }

    @Override
    public String toString() {
        return "Request{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", queryParams=" + queryParams +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
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
