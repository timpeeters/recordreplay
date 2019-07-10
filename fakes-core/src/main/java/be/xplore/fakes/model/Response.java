package be.xplore.fakes.model;

import java.util.Objects;

public class Response {

    private int statusCode;
    private String statusText;

    public Response() {
        this.statusCode = 200;
        this.statusText = "OK";
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Response response = (Response) o;
        return this.statusCode == response.getStatusCode() &&
                this.statusText.equals(response.getStatusText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, statusText);
    }
}
