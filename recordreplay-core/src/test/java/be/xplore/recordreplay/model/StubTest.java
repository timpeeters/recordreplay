package be.xplore.recordreplay.model;


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
        Stub stub1 = getNewExampleStub1();
        Stub stub2 = getNewExampleStub2();
        assertThat(stub1).isNotEqualTo(stub2);
        assertThat(stub1).isNotEqualTo(new Stub(stub1.getRequest(), stub2.getResponse()));
        assertThat(stub1).isNotEqualTo(new Stub(stub2.getRequest(), stub1.getResponse()));
    }

    @Test
    public void checkNotEqualNoThrow() {
        assertThat(getNewExampleStub1()).isNotEqualTo(null);
        assertThat(getNewExampleStub1()).isNotEqualTo("I'm obviously not a Stub");
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
        return new Stub(
                Request.builder().method(RequestMethod.GET).path("/test").build(),
                Response.builder().statusCode(404).statusText("NOT FOUND").build()

        );
    }

    private Stub getNewExampleStub2() {
        return new Stub(
                Request.builder().method(RequestMethod.POST).path("/test").build(),
                Response.ok()
        );
    }

}
