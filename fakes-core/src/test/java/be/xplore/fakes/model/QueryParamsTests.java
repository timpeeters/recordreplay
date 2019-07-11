package be.xplore.fakes.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class QueryParamsTests {

    private static List<String> someParams = new ArrayList<>();
    private static Map<String, List<String>> paramMap = new HashMap<>();
    private static QueryParams queryParams;

    @BeforeClass
    public static void setUpParamMap() {
        someParams.add("paramTest1");
        someParams.add("paramTest2");
        paramMap.put("Key", someParams);
        queryParams = QueryParams.builder().params(paramMap).build();
    }

    @Test
    public void getParams() {
    }

    @Test
    public void builder() {
    }

    @Test
    public void isEmptyShouldReturnTrueOnNullMap() {
        assertThat(QueryParams.builder().params(null).build().isEmpty())
                .as("Null paramsMap not detected")
                .isTrue();
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
    public void toStringShouldReturnUriParamString() {
        assertThat(queryParams.getQueryString())
                .as("toString does not format to correct URL format")
                .isEqualTo("?Key=paramTest1&Key=paramTest2");
    }
}
