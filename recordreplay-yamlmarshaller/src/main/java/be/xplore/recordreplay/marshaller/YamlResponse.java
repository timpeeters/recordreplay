package be.xplore.recordreplay.marshaller;

import be.xplore.recordreplay.model.Response;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
class YamlResponse {
    public int statusCode;
    public String statusText;
    public Map<String, List<String>> headers;
    public String body;

    YamlResponse() {
    }

    YamlResponse(Response response) {
        this.statusCode = response.getStatusCode();
        this.statusText = response.getStatusText();
        this.headers = response.getHeaders().getModifiableHeaderMap();
        this.body = response.getBody();
    }

    Response toResponse() {
        return Response.builder().statusCode(statusCode).statusText(statusText).build();
    }
}
