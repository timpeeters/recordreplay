package be.xplore.fakes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {
    private static final Request.Builder EXAMPLE_REQUEST_BUILDER_1 = Request.Builder.get("/test");

    private static final Request.Builder EXAMPLE_REQUEST_BUILDER_2 = Request.Builder.post("/abc");

    private static final String PATH_1 = "/abc";
    private static final String PATH_2 = "/xyz";

    @Test
    @SuppressWarnings({"checkstyle:executablestatementcount", "PMD.JUnitTestContainsTooManyAsserts"})
    public void requestBuilder() {
        Request request = Request.builder().method(RequestMethod.GET).path(PATH_1).build();
        assertThat(request).isNotNull();
        assertThat(request.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(request.getPath()).isEqualTo(PATH_1);
        assertThat(request.getParams()).isNotNull().isEmpty();
        assertThat(request.getHeaders()).isNotNull().isEmpty();
        assertThat(request.getBody()).isNotNull().isEmpty();
    }

    @Test
    public void checkEquality() {
        Request request = EXAMPLE_REQUEST_BUILDER_1.build();
        assertThat(request).isEqualTo(request);
        assertThat(EXAMPLE_REQUEST_BUILDER_1.build()).as("Equals method not implemented correctly")
                .isEqualTo(request);
    }

    @Test
    public void checkNotEqual() {
        assertThat(EXAMPLE_REQUEST_BUILDER_1.build()).isNotEqualTo(EXAMPLE_REQUEST_BUILDER_2.build());
        assertThat(EXAMPLE_REQUEST_BUILDER_1.path(PATH_1).build())
                .isNotEqualTo(EXAMPLE_REQUEST_BUILDER_1.path(PATH_2).build());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Request request = EXAMPLE_REQUEST_BUILDER_1.build();
        Request nullRequest = null;

        assertThat(nullRequest).isNotEqualTo(request);
        assertThat(5).isNotEqualTo(request);
    }

    @Test
    public void hashCodeTest() {
        assertThat(EXAMPLE_REQUEST_BUILDER_1.build()).hasSameHashCodeAs(EXAMPLE_REQUEST_BUILDER_1.build());
        assertThat(EXAMPLE_REQUEST_BUILDER_1.build().hashCode())
                .isNotEqualTo(EXAMPLE_REQUEST_BUILDER_2.build().hashCode());
    }
}
