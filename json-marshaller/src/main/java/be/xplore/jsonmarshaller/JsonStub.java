package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Stub;

class JsonStub {
    public JsonRequest request;
    public JsonResponse response;

    Stub toStub() {
        return new Stub(request.toRequest(), response.toResponse());
    }
}
