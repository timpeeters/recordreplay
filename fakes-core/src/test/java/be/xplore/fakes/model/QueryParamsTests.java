package be.xplore.fakes.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class QueryParamsTests {

    private final List<String> someParams = List.of("paramTest1", "paramTest2");
    private final Map<String, List<String>> paramMap = Map.of("Key", someParams);
    private final QueryParams queryParams = QueryParams.builder().params(paramMap).build();

    @Test
    public void getParams() {
        assertThat(queryParams.getParams())
                .as("Doesn't return a paramMap")
                .isEqualTo(paramMap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildingQueryWithNullShouldThrow() {
        QueryParams.builder().params(null).build();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void changingParamMapShoudlThrow() {
        QueryParams.builder().params(new HashMap<>()).build().getParams().remove("Key");
    }


    @Test
    public void isEmptyShouldReturnTrueOnEmptyMap() {
        assertThat(QueryParams.builder().params(new HashMap<>()).build().isEmpty())
                .as("Empty paramsMap not detected")
                .isTrue();
    }

    @Test
    public void isEmptyShouldReturnFalseOnFilledMap() {
        assertThat(QueryParams.builder().params(paramMap).build().isEmpty())
                .as("Filled paramsMap not detected")
                .isFalse();
    }


    @Test
    public void getQueryParamsShouldReturnUriParamString() {
        assertThat(queryParams.getQueryString())
                .as("getQueryString does not format to correct URL format")
                .isEqualTo("?Key=paramTest1&Key=paramTest2");
    }
}
