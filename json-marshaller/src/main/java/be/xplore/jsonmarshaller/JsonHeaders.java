package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Headers;

import java.util.List;
import java.util.Map;

public class JsonHeaders {

    public Map<String, List<String>> headerMap;

    Headers toHeaders() {
        return Headers.builder().headerMap(this.headerMap).build();
    }
}
