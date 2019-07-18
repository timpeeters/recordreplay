package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Response;

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
        this.headers = response.getHeaders().getHeaderMap();
        this.body = response.getBody();
    }

    Response toResponse() {
        return Response.builder().statusCode(statusCode).statusText(statusText).build();
    }
}
