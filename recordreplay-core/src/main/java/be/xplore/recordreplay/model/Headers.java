package be.xplore.recordreplay.model;

import be.xplore.recordreplay.util.Assert;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Headers {

    public static final Headers EMPTY = builder().headerMap(Collections.emptyMap()).build();
    private final Map<String, List<String>> headerMap;

    private Headers(Builder builder) {
        this.headerMap = Collections.unmodifiableMap(Assert.notNull(builder.headerMap));
    }

    public Headers(HttpHeaders headers) {
        this.headerMap = Collections.unmodifiableMap(headers.map());
    }

    public Map<String, List<String>> getModifiableHeaderMap() {
        return new HashMap<>(headerMap);
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
        return headerMap.entrySet()
                .stream()
                .flatMap(concatKeyValue())
                .collect(Collectors.toList());
    }

    private Function<Map.Entry<String, List<String>>, Stream<? extends String>> concatKeyValue() {
        return set -> set.getValue().stream()
                .map(value -> set.getKey().concat(value));
    }

    public String[] toVarargs() {
        return headerMap.entrySet()
                .stream()
                .flatMap(getKeyValueList())
                .flatMap(Collection::stream)
                .collect(Collectors.toList()).toArray(String[]::new);
    }

    private Function<Map.Entry<String, List<String>>, Stream<? extends List<String>>> getKeyValueList() {
        return set -> set.getValue().stream()
                .map(value -> List.of(set.getKey(), value));
    }

    public int size() {
        return headerMap.values().stream().mapToInt(List::size).sum();
    }

    public boolean isEmpty() {
        return headerMap.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Headers otherHeaders = (Headers) o;
            return Objects.equals(this.headerMap, otherHeaders.headerMap);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerMap);
    }

    @Override
    public String toString() {
        return new StringJoiner(",", "Headers{", "}")
                .add("headerMap=" + headerMap)
                .toString();
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
