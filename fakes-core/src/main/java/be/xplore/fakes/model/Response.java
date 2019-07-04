package be.xplore.fakes.model;

public class Response {

    private int statusCode;
    private String statusText;

    public int getStatusCode() {
        return statusCode;
    }

    public Response setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    public Response setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }
}
