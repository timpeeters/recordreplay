package be.xplore.recordreplay.model;

import be.xplore.recordreplay.util.Assert;

import java.util.Objects;

public class Response {

    private final int statusCode;
    private final String statusText;
    private final Headers headers;
    private final String body;

    private Response(Builder builder) {
        this.statusCode = Assert.notNull(builder.statusCode);
        this.statusText = builder.statusText;
        this.headers = builder.headers;
        this.body = builder.body;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Response otherResponse = (Response) o;
            return this.statusCode == otherResponse.statusCode &&
                    Objects.equals(this.statusText, otherResponse.statusText) &&
                    Objects.equals(this.headers, otherResponse.headers) &&
                    Objects.equals(this.body, otherResponse.body);
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, statusText, headers, body);
    }

    @Override
    public String toString() {
        return "Response{" +
                "statusCode=" + statusCode +
                ", statusText='" + statusText + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private Integer statusCode;
        private String statusText;
        private Headers headers;
        private String body;

        private Builder() {
            statusText = "";
            headers = Headers.EMPTY;
            body = "";
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

        public Builder headers(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
