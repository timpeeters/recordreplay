package be.xplore.fakes.model;

import be.xplore.fakes.util.Assert;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        headerMap.forEach((key, value1) -> value1
                .forEach(value -> {
                    headerList.add(key);
                    headerList.add(value);
                }));
        return headerList.toArray(String[]::new);
    }

    public Headers copyOf() {
        return Headers.builder().headerMap(this.headerMap).build();
    }

    public int size() {
        return toStringList().size();
    }

    public boolean isEmpty() {
        return headerMap.isEmpty();
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

        public Headers build() {
            return new Headers(this);
        }

    }

}
