package be.xplore.fakes.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StubTest {

    @Test
    public void checkEquality() {
        assertThat(getNewExampleStub1()).as("Equals method not implemented correctly").isEqualTo(getNewExampleStub1());
        Stub stub = getNewExampleStub1();
        assertThat(stub).isEqualTo(stub);
    }

    @Test
    public void checkNotEqual() {
        assertThat(getNewExampleStub2()).isNotEqualTo(getNewExampleStub1());
        assertThat(getNewExampleStub1().setResponse(getNewExampleStub2().getResponse()))
                .isNotEqualTo(getNewExampleStub1());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Stub stub = getNewExampleStub1();
        Stub nullStub = null;
        assertThat(nullStub).isNotEqualTo(stub);
        assertThat(5).isNotEqualTo(stub);
    }

    @Test
    public void hashCodeTest() {
        assertThat(getNewExampleStub1().hashCode()).isEqualTo(getNewExampleStub1().hashCode());
        assertThat(getNewExampleStub2().hashCode()).isNotEqualTo(getNewExampleStub1().hashCode());
    }

    private Stub getNewExampleStub1() {
        return new Stub()
                .setResponse(new Response()
                        .setStatusCode(404)
                        .setStatusText("NOT FOUND"))
                .setRequest(new Request()
                        .setPath("/test")
                        .setMethod(RequestMethod.GET));
    }

    private Stub getNewExampleStub2() {
        return new Stub()
                .setResponse(new Response()
                        .setStatusCode(200)
                        .setStatusText("OK"))
                .setRequest(new Request()
                        .setPath("/abc")
                        .setMethod(RequestMethod.POST));
    }

}
