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

    private final Map<String, List<String>> queryMap;

    private QueryParams(Builder builder) {
        this.queryMap = Collections.unmodifiableMap(Assert.notNull(builder.queryMap));
    }

    public Map<String, List<String>> getModifiableQueryMap() {
        return new HashMap<>(queryMap);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getQueryString() {
        return queryMap.entrySet()
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
        return queryMap.entrySet()
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
        return queryMap.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            QueryParams otherQuery = (QueryParams) o;
            return Objects.equals(this.queryMap, otherQuery.queryMap);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.queryMap);
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "queryParams=" + queryMap +
                '}';
    }

    public static class Builder {

        private Map<String, List<String>> queryMap;

        private Builder() {
            queryMap = new HashMap<>();
        }

        public Builder params(Map<String, List<String>> queryMap) {
            this.queryMap = queryMap;
            return this;
        }

        public Builder param(String key, String value) {
            List<String> values = queryMap.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public QueryParams build() {
            return new QueryParams(this);
        }

    }
}
