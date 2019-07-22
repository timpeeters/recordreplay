package be.xplore.fakes.model;

import be.xplore.fakes.util.Assert;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Headers {

    public static final Headers EMPTY = builder().headerMap(Collections.emptyMap()).build();
    private final Map<String, List<String>> headerMap;

    private Headers(Builder builder) {
        this.headerMap = Collections.unmodifiableMap(Assert.notNull(builder.headerMap));
    }

    public Headers(HttpHeaders headers) {
        this.headerMap = Collections.unmodifiableMap(headers.map());
    }

    public Map<String, List<String>> getHeaderMap() {
        return headerMap;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> returnMismatchingHeaders(Headers headers) {
        return this.toStringList().stream()
                .filter(s -> !headers.toStringList().contains(s))
                .collect(Collectors.toList());
    }

    public List<String> toStringList() {
        List<String> headerStrings = new ArrayList<>();
        headerMap.forEach((key, value1) -> value1
                .forEach(value ->
                        headerStrings.add(key.concat(value)
                        )));
        return headerStrings;
    }

    public String[] toVarargs() {
        List<String> headerList = new ArrayList<>();
        headerMap.forEach((key, valueList) -> valueList
                .forEach(value -> {
                    headerList.add(key);
                    headerList.add(value);
                }));
        return headerList.toArray(String[]::new);
    }

    public int size() {
        return headerMap.values().stream().mapToInt(List::size).sum();
    }

    public boolean isEmpty() {
        return headerMap.isEmpty();
    }

    @SuppressWarnings("checkstyle:ExecutableStatementCount")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Headers headers = (Headers) o;
        if (this.size() != headers.size()) {
            return false;
        }
        return headerMap.entrySet().stream()
                .allMatch(entry -> entry.getValue().equals(headers.getHeaderMap().get(entry.getKey()))
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerMap);
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headerMap=" + headerMap +
                '}';
    }

    public static class Builder {

        private Map<String, List<String>> headerMap;

        private Builder() {
            headerMap = new HashMap<>();
        }

        public Builder headerMap(Map<String, List<String>> headerMap) {
            this.headerMap = headerMap;
            return this;
        }

        public Builder header(String key, String value) {
            List<String> values = headerMap.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public Builder applicationJson() {
            header("Content-Type", "application/json");
            return this;
        }

        public Builder applicationXml() {
            header("Content-Type", "application/soap+xml");
            return this;
        }

        public Headers build() {
            return new Headers(this);
        }

    }

}
