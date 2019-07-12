package be.xplore.fakes.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadersTests {


    private static List<String> someHeaders = new ArrayList<>();
    private static List<String> expectedStringList = new ArrayList<>();
    private static String[] expectedVarargs = {"Key", "headerTest1", "Key", "headerTest2"};
    private static Map<String, List<String>> headerMap = new HashMap<>();
    private static Headers headers;

    @BeforeClass
    public static void setUpHeaderMap() {
        someHeaders.add("headerTest1");
        someHeaders.add("headerTest2");
        headerMap.put("Key", someHeaders);
    }

    @BeforeClass
    public static void setUpExpectedStrings() {
        expectedStringList.add("KeyheaderTest1");
        expectedStringList.add("KeyheaderTest2");
    }

    @BeforeClass
    public static void setUpHeaders(){
        headers = Headers.builder().headerMap(headerMap).build();
    }

    @Test
    public void getHeadersShouldReturnMapOfHeaders() {
        assertThat(headers.getHeaderMap())
                .as("doesn't return correct map of headerMap")
                .isEqualTo(headerMap);
    }

    @Test
    public void builderShoudlReturnNewBuilderObject() {
        assertThat(Headers.builder())
                .as("Doesn't return a new Builder object")
                .isInstanceOf(Headers.Builder.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildingHeaderWithNullShouldThrow() {
        Headers.builder().headerMap(null).build();
    }

    @Test
    public void isEmptyShouldReturnTrueOnEmptyMap() {
        assertThat(Headers.builder().headerMap(new HashMap<>()).build().isEmpty())
                .as("Empty paramsMap not detected")
                .isTrue();
    }

    @Test
    public void isEmptyShouldReturnFalseOnFilledMap() {
        assertThat(Headers.builder().headerMap(headerMap).build().isEmpty())
                .as("Filled paramsMap not detected")
                .isFalse();
    }

    @Test
    public void getHeaderVarargsShouldReturnArrayOfLists() {
        assertThat(Arrays.equals(headers.toVarargs(), expectedVarargs))
                .as("Varargs not generated correctly")
                .isTrue();
    }

    @Test
    public void getHeaderAsKeyValueStringShouldReturnListOfStrings() {
        assertThat(headers.toStringList().equals(expectedStringList))
                .as("Key-Value String-list not generated correctly")
                .isTrue();
    }

}
