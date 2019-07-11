package be.xplore.fakes.model;

import java.util.Objects;

public class Stub {

    private Request request;
    private Response response;

    public Stub(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Stub setRequest(Request request) {
        this.request = request;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public Stub setResponse(Response response) {
        this.response = response;
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
        Stub stub = (Stub) o;
        return this.request.equals(stub.getRequest()) &&
                this.response.equals(stub.getResponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response);
    }
}
