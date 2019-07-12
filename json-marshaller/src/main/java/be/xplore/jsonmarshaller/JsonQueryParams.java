package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.QueryParams;

import java.util.List;
import java.util.Map;

public class JsonQueryParams {

    public Map<String, List<String>> params;

    QueryParams toQueryParams() {
        return QueryParams.builder().params(this.params).build();
    }

}
