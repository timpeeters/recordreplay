package be.xplore.fakes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Headers {

    private final Map<String, List<String>> headerMap;

    private Headers(Builder builder) {
        this.headerMap = builder.headerMap;
    }

    public Map<String, List<String>> getHeaderMap() {
        return headerMap;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isEmpty() {
        if (headerMap == null) {
            return true;
        }
        return headerMap.isEmpty();
    }

    public String[] getHeaderVarargs() {
        List<String> headerList = new ArrayList<>();
        headerMap.forEach((key, value1) -> value1
                .forEach(value -> {
                    headerList.add(key);
                    headerList.add(value);
                }));
        return headerList.toArray(String[]::new);
    }

    public static class Builder {

        private Map<String, List<String>> headerMap;

        public Builder headerMap(Map<String, List<String>> headerMap) {
            this.headerMap = headerMap;

            return this;
        }

        public Headers build() {
            return new Headers(this);
        }

    }

}
