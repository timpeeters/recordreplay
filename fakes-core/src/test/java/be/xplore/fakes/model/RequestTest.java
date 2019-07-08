package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RequestTest {

    @Test
    public void checkEquality() {
        Request request1 = getNewExampleRequest();
        Request request2 = getNewExampleRequest();
        assertEquals(request1, request1);
        assertEquals("Equals method not implemented correctly", request1, request2);
    }

    @Test
    public void checkNotEqual() {
        Request request1 = getNewExampleRequest();
        Request request2 = new Request()
                .setMethod(RequestMethod.POST);
        assertNotEquals(request1, request2);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Request request1 = getNewExampleRequest();
        Request request2 = null;

        assertNotEquals(request1, request2);
        assertNotEquals(request1, 5);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(getNewExampleRequest().hashCode(), getNewExampleRequest().hashCode());
        assertNotEquals(getNewExampleRequest().hashCode(),
                getNewExampleRequest().setMethod(RequestMethod.POST).hashCode());
        assertNotEquals(getNewExampleRequest().hashCode(), getNewExampleRequest().setPath("/abc").hashCode());
    }


    private Request getNewExampleRequest() {
        return new Request()
                .setPath("/test")
                .setMethod(RequestMethod.GET);
    }
}
