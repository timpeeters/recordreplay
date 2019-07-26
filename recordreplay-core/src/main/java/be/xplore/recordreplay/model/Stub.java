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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stub stub = (Stub) o;
        return this.request.equals(stub.getRequest()) &&
                this.response.equals(stub.getResponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response);
    }
}