package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Stub;

class JsonStub {
    public JsonRequest request;
    public JsonResponse response;

    JsonStub() {
    }

    JsonStub(Stub stub) {
        this.request = new JsonRequest(stub.getRequest());
        this.response = new JsonResponse(stub.getResponse());
    }

    Stub toStub() {
        return new Stub(request.toRequest(), response.toResponse());
    }
}
