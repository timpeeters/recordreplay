package be.xplore.fakes.model;

public class Stub {

    private Request request;
    private Response response;

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
}
