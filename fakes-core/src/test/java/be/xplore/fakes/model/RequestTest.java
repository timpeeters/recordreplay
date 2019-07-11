package be.xplore.fakes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    public void requestBuilder() {
        Request request = Request.builder().method(RequestMethod.GET).path("/abc").build();
        assertThat(request).isNotNull();
        assertThat(request.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(request.getPath()).isEqualTo("/abc");
        assertThat(request.getParams()).isNotNull().isEmpty();
        assertThat(request.getHeaders()).isNotNull().isEmpty();
        assertThat(request.getBody()).isNotNull().isEmpty();
    }

    @Test
    public void checkEquality() {
        Request request = getNewExampleRequest1();
        assertThat(request).isEqualTo(request);
        assertThat(getNewExampleRequest1()).as("Equals method not implemented correctly").isEqualTo(request);
    }

    @Test
    public void checkNotEqual() {
        assertThat(getNewExampleRequest2()).isNotEqualTo(getNewExampleRequest1());
        assertThat(getNewExampleRequest1().setPath(getNewExampleRequest2().getPath()))
                .isNotEqualTo(getNewExampleRequest1());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Request request = getNewExampleRequest1();
        Request nullRequest = null;

        assertThat(nullRequest).isNotEqualTo(request);
        assertThat(5).isNotEqualTo(request);
    }

    @Test
    public void hashCodeTest() {
        assertThat(getNewExampleRequest1().hashCode()).isEqualTo(getNewExampleRequest1().hashCode());
        assertThat(getNewExampleRequest2()).isNotEqualTo(getNewExampleRequest1().hashCode());
    }


    private Request getNewExampleRequest1() {
        return new Request()
                .setPath("/test")
                .setMethod(RequestMethod.GET);
    }

    private Request getNewExampleRequest2() {
        return new Request()
                .setPath("/abc")
                .setMethod(RequestMethod.POST);
    }
}
