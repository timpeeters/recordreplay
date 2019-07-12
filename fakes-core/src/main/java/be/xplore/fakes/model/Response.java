package be.xplore.fakes.model;

import be.xplore.fakes.util.Assert;

import java.util.Objects;

public class Response {

    private final int statusCode;
    private final String statusText;

    private Response(Builder builder) {
        this.statusCode = Assert.notNull(builder.statusCode);
        this.statusText = builder.statusText;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Response ok() {
        return Builder.ok().build();
    }

    public static Response notFound() {
        return Builder.notFound().build();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
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

        public static Builder ok() {
            return new Builder().statusCode(200).statusText("OK");
        }

        public static Builder notFound() {
            return new Builder().statusCode(404).statusText("NOT FOUND");
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
