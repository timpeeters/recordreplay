package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Response;

class JsonResponse {
    public int statusCode;
    public String statusText;

    Response toResponse() {
        return Response.builder().statusCode(statusCode).statusText(statusText).build();
    }
}
