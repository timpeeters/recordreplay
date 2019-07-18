package be.xplore.fakes.model;

import org.assertj.core.util.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadersTests {

    private final String[] expectedVarargs = {"Key", "headerTest1", "Key", "headerTest2"};
    private final List<String> expectedStringList = List.of("KeyheaderTest1", "KeyheaderTest2");
    private final Map<String, List<String>> headerMap = Maps.newHashMap("Key", List.of("headerTest1", "headerTest2"));
    private final Headers.Builder builder = Headers.builder().headerMap(headerMap);
    private final Headers headers = builder.build();

    @Test
    public void getHeadersShouldReturnMapOfHeaders() {
        assertThat(headers.getHeaderMap())
                .as("doesn't return correct map of headerMap")
                .isEqualTo(headerMap);
    }

    @Test
    public void builderShouldReturnNewBuilderObject() {
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

    @Test
    public void checkEquality() {
        assertThat(headers).isEqualTo(headers);
        assertThat(headers).isEqualTo(builder.build());
    }

    @Test
    public void checkNotEqual() {
        assertThat(headers)
                .isNotEqualTo(builder.headerMap(Map.of("uygbzeg", List.of("izrgbét", "ogveqqyn", "çzugbqdv")))
                        .build());
        assertThat(headers)
                .isNotEqualTo(builder.headerMap(Map.of("uygbzeg", List.of("izrgbét", "ogveqqyn")))
                        .build());
    }

    @Test
    public void checkNullAndOtherClass() {
        assertThat(headers).isNotEqualTo(null);
        assertThat(headers).isNotEqualTo("I'm not a Request");
    }

}
