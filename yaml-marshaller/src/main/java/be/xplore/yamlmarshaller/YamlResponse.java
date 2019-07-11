package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Response;

class YamlResponse {
    public int statusCode;
    public String statusText;

    Response toResponse() {
        return Response.builder().statusCode(statusCode).statusText(statusText).build();
    }
}
