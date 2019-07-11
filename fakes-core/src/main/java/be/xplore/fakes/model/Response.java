package be.xplore.fakes.model;

import be.xplore.fakes.util.Assert;

import java.util.Objects;

public class Response {

    private int statusCode;
    private String statusText;

    private Response(Builder builder) {
        this.statusCode = Assert.notNull(builder.statusCode);
        this.statusText = builder.statusText;
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private Integer statusCode;
        private String statusText;

        private Builder() {
            statusText = "";
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder statusText(String statusText) {
            this.statusText = statusText;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
