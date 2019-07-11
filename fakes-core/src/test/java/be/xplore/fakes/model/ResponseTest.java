package be.xplore.fakes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

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
        assertThat(getNewExampleResponse2()).isNotEqualTo(getNewExampleResponse1());
        assertThat(getNewExampleResponse1().setStatusText(getNewExampleResponse2().getStatusText()))
                .isNotEqualTo(getNewExampleResponse1());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Response response = getNewExampleResponse1();
        Response nullResponse = null;
        assertThat(nullResponse).isNotEqualTo(response);
        assertThat(5).isNotEqualTo(response);
    }

    @Test
    public void hashCodeTest() {
        assertThat(getNewExampleResponse1().hashCode()).isEqualTo(getNewExampleResponse1().hashCode());
        assertThat(getNewExampleResponse2().hashCode()).isNotEqualTo(getNewExampleResponse1().hashCode());
    }

    private Response getNewExampleResponse1() {
        return Response.builder()
                .statusCode(200)
                .statusText("OK")
                .build();
    }

    private Response getNewExampleResponse2() {
        return Response.builder()
                .statusCode(404)
                .statusText("NOT FOUND")
                .build();
    }

}
