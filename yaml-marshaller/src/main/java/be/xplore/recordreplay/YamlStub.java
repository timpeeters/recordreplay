package be.xplore.recordreplay;

import be.xplore.recordreplay.model.Stub;

class YamlStub {
    public YamlRequest request;
    public YamlResponse response;

    YamlStub() {
    }

    YamlStub(Stub stub) {
        this.request = new YamlRequest(stub.getRequest());
        this.response = new YamlResponse(stub.getResponse());
    }

    Stub toStub() {
        return new Stub(request.toRequest(), response.toResponse());
    }
}
