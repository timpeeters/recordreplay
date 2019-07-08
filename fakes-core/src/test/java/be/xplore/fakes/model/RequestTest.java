package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RequestTest {

    @Test
    public void checkEquality() {
        Request request = getNewExampleRequest1();
        assertEquals(request, request);
        assertEquals("Equals method not implemented correctly", request, getNewExampleRequest1());
    }

    @Test
    public void checkNotEqual() {
        assertNotEquals(getNewExampleRequest1(), getNewExampleRequest2());
        assertNotEquals(getNewExampleRequest1(), getNewExampleRequest1().setPath(getNewExampleRequest2().getPath()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Request request = getNewExampleRequest1();
        Request nullRequest = null;

        assertNotEquals(request, nullRequest);
        assertNotEquals(request, 5);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(getNewExampleRequest1().hashCode(), getNewExampleRequest1().hashCode());
        assertNotEquals(getNewExampleRequest1().hashCode(),getNewExampleRequest2());
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
