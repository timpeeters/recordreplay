package be.xplore.fakes.model;

import java.util.Objects;

public class Request {

    private RequestMethod method;
    private String path;

    public Request() {
        this.method = RequestMethod.GET;
        this.path = "";
    }

    public Request(RequestMethod method, String path) {
        this.method = method;
        this.path = path;
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
}
