package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StubTest {

    @Test
    public void checkEquality() {
        assertEquals("Equals method not implemented correctly", getNewExampleStub1(), getNewExampleStub1());
        Stub stub = getNewExampleStub1();
        assertEquals(stub, stub);
    }

    @Test
    public void checkNotEqual() {
        assertNotEquals(getNewExampleStub1(), getNewExampleStub2());
        assertNotEquals(getNewExampleStub1(), getNewExampleStub1().setResponse(getNewExampleStub2().getResponse()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Stub stub = getNewExampleStub1();
        Stub nullStub = null;
        assertNotEquals(stub, nullStub);
        assertNotEquals(stub, 5);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(getNewExampleStub1().hashCode(), getNewExampleStub1().hashCode());
        assertNotEquals(getNewExampleStub1().hashCode(), getNewExampleStub2().hashCode());
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
