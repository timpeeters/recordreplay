package be.xplore.recordreplay.model;

import java.util.Objects;

public class Stub {

    private final Request request;
    private final Response response;

    public Stub(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Stub otherStub = (Stub) o;
            return Objects.equals(this.request, otherStub.request) &&
                    Objects.equals(this.response, otherStub.response);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response);
    }

    @Override
    public String toString() {
        return "Stub{" +
                "request=" + request +
                ", response=" + response +
                '}';
    }
}
