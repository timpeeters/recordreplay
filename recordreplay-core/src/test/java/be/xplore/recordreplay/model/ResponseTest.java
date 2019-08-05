package be.xplore.recordreplay.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

    private static final Response.Builder RESPONSE_BUILDER_1 = Response.Builder.ok();

    @Test

    public void responseBuilder() {
        Response response = Response.builder().statusCode(200).statusText("OK").build();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getStatusText()).isEqualTo("OK");

        response = Response.builder().statusCode(200).build();
        assertThat(response.getStatusText()).isNotNull().isEmpty();
    }

    @Test
    public void checkEquality() {
        Response response = getNewExampleResponse1();
        assertThat(response).isEqualTo(response);
        assertThat(getNewExampleResponse1()).as("Equals method not implemented correctly").isEqualTo(response);
    }

    @Test
    public void checkNotEqual() {
        assertThat(getNewExampleResponse1()).isNotEqualTo(getNewExampleResponse2());
        assertThat(RESPONSE_BUILDER_1.statusText("abc").build())
                .isNotEqualTo(RESPONSE_BUILDER_1.statusText("xyz").build());
        assertThat(RESPONSE_BUILDER_1.headers(Headers.builder().header("key", "value").build()).build())
                .isNotEqualTo(RESPONSE_BUILDER_1.headers(Headers.EMPTY).build());
        assertThat(RESPONSE_BUILDER_1.body("one").build())
                .isNotEqualTo(RESPONSE_BUILDER_1.body("two").build());
    }

    @Test
    public void checkNullAndOtherClass() {
        Response response = getNewExampleResponse1();
        assertThat(response).isNotEqualTo(null);
        assertThat(response).isNotEqualTo("I'm not a response");
    }

    @Test
    public void hashCodeTest() {
        assertThat(getNewExampleResponse1().hashCode()).isEqualTo(getNewExampleResponse1().hashCode());
        assertThat(getNewExampleResponse2().hashCode()).isNotEqualTo(getNewExampleResponse1().hashCode());
    }

    @Test
    public void checkToString() {
        assertThat(getNewExampleResponse1().toString())
                .isEqualTo("Response{statusCode=200,statusText=OK," +
                        "headers=Headers{headerMap={}},body=}");
    }


    private Response getNewExampleResponse1() {
        return Response.ok();
    }


    private Response getNewExampleResponse2() {
        return Response.notFound();
    }
}
