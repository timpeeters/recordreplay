package be.xplore.fakes.model;

import be.xplore.fakes.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParams {

    public static final QueryParams EMPTY = builder().params(Collections.emptyMap()).build();

    private final Map<String, List<String>> params;

    private QueryParams(Builder builder) {
        this.params = Collections.unmodifiableMap(Assert.notNull(builder.params));
    }

    public Map<String, List<String>> getParams() {
        return params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getQueryString() {
        StringBuilder paramString = new StringBuilder();
        params.forEach((key, value) -> value
                .forEach(string -> {
                    paramString.append(key)
                            .append('=')
                            .append(string)
                            .append('&');
                }));
        return paramString.insert(0, '?').substring(0, paramString.length() - 1);
    }

    public List<String> returnMismatchingQueries(QueryParams params) {
        return this.toStringList().stream()
                .filter(s -> !params.toStringList().contains(s))
                .collect(Collectors.toList());
    }

    private List<String> toStringList() {
        List<String> paramStrings = new ArrayList<>();
        params.forEach((key, value1) -> value1
                .forEach(value ->
                        paramStrings.add(key.concat(value)
                        )));
        return paramStrings;
    }

    public int size() {
        //TODO implement shit
        return 0;
    }

    public QueryParams copyOf() {
        return QueryParams.builder().params(this.getParams()).build();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "queryParams=" + params +
                '}';
    }

    public static class Builder {

        private Map<String, List<String>> params;

        private Builder() {
        }

        public Builder params(Map<String, List<String>> params) {
            this.params = params;
            return this;
        }

        public QueryParams build() {
            return new QueryParams(this);
        }

    }
}
