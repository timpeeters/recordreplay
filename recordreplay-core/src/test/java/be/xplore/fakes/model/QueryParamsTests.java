package be.xplore.fakes.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class QueryParamsTests {

    private final List<String> someParams = List.of("paramTest1", "paramTest2");
    private final Map<String, List<String>> paramMap = Map.of("Key", someParams);
    private final QueryParams.Builder builder = QueryParams.builder().params(paramMap);
    private final QueryParams queryParams = builder.build();

    @Test
    public void getParams() {
        assertThat(queryParams.getModifiableParamMap())
                .as("Doesn't return a paramMap")
                .isEqualTo(paramMap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildingQueryWithNullShouldThrow() {
        QueryParams.builder().params(null).build();
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

    @Test
    public void checkEquality() {
        assertThat(queryParams).isEqualTo(queryParams);
        assertThat(queryParams).isEqualTo(builder.build());
    }

    @Test
    public void checkNotEqual() {
        assertThat(queryParams)
                .isNotEqualTo(builder.params(Map.of("uygbzeg", List.of("izrgbét", "ogveqqyn", "çzugbqdv")))
                        .build());
        assertThat(queryParams)
                .isNotEqualTo(builder.params(Map.of("uygbzeg", List.of("izrgbét", "ogveqqyn")))
                        .build());
    }

    @Test
    public void checkNullAndOtherClass() {
        assertThat(queryParams).isNotEqualTo(null);
        assertThat(queryParams).isNotEqualTo("I'm a String");
    }
}
