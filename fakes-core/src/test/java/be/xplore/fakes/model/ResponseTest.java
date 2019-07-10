package be.xplore.fakes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

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
        return new Response()
                .setStatusCode(200)
                .setStatusText("OK");
    }

    private Response getNewExampleResponse2() {
        return new Response()
                .setStatusCode(404)
                .setStatusText("NOT FOUND");
    }

}
