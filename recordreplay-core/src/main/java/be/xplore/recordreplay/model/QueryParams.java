package be.xplore.recordreplay.model;

import be.xplore.recordreplay.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParams {

    public static final QueryParams EMPTY = builder().params(Collections.emptyMap()).build();

    private final Map<String, List<String>> params;

    private QueryParams(Builder builder) {
        this.params = Collections.unmodifiableMap(Assert.notNull(builder.params));
    }

    public Map<String, List<String>> getModifiableParamMap() {
        return new HashMap<>(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getQueryString() {
        return params.entrySet()
                .stream()
                .flatMap(formatAsQuery())
                .collect(Collectors.joining("&", "?", ""));
    }

    private Function<Map.Entry<String, List<String>>, Stream<? extends StringBuilder>> formatAsQuery() {
        return set -> set.getValue()
                .stream()
                .map(value -> new StringBuilder(set.getKey() + "=" + value));
    }

    public List<String> returnMismatchingQueries(QueryParams params) {
        return this.toStringList().stream()
                .filter(s -> !params.toStringList().contains(s))
                .collect(Collectors.toList());
    }

    private List<String> toStringList() {
        return params.entrySet()
                .stream()
                .flatMap(concatKeyValue())
                .collect(Collectors.toList());
    }

    private Function<Map.Entry<String, List<String>>, Stream<? extends String>> concatKeyValue() {
        return set -> set.getValue()
                .stream()
                .map(value -> set.getKey() + value);
    }

    public int size() {
        return this.toStringList().size();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryParams otherParams = (QueryParams) o;
        return this.params.entrySet().stream()
                .allMatch(entry -> entry.getValue().equals(otherParams.getModifiableParamMap().get(entry.getKey()))
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    public static class Builder {

        private Map<String, List<String>> params;

        private Builder() {
            params = new HashMap<>();
        }

        public Builder params(Map<String, List<String>> params) {
            this.params = params;
            return this;
        }

        public Builder param(String key, String value) {
            List<String> values = params.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public QueryParams build() {
            return new QueryParams(this);
        }

    }
}
