package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Stub;

class YamlStub {
    public YamlRequest request;
    public YamlResponse response;

    Stub toStub() {
        return new Stub(request.toRequest(), response.toResponse());
    }
}
