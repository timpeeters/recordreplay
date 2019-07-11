package be.xplore.fakes.model;

import java.util.List;
import java.util.Map;

public class QueryParams {

    private final Map<String, List<String>> params;

    private QueryParams(Builder builder) {
        this.params = builder.params;
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

    public boolean isEmpty() {
        if (params == null) {
            return true;
        }
        return params.isEmpty();
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "params=" + params +
                '}';
    }

    public static class Builder {

        private Map<String, List<String>> params;

        public Builder params(Map<String, List<String>> params) {
            this.params = params;

            return this;
        }

        public QueryParams build() {
            return new QueryParams(this);
        }

    }
}
