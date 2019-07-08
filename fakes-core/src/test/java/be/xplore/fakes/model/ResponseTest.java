package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ResponseTest {

    @Test
    public void checkEquality() {
        Response response = getNewExampleResponse1();
        assertEquals(response, response);
        assertEquals("Equals method not implemented correctly", response, getNewExampleResponse1());
    }

    @Test
    public void checkNotEqual() {
        assertNotEquals(getNewExampleResponse1(), getNewExampleResponse2());
        assertNotEquals(getNewExampleResponse1(),
                getNewExampleResponse1().setStatusText(getNewExampleResponse2().getStatusText()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Response response = getNewExampleResponse1();
        Response nullResponse = null;
        assertNotEquals(response, nullResponse);
        assertNotEquals(response, 5);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(getNewExampleResponse1().hashCode(), getNewExampleResponse1().hashCode());
        assertNotEquals(getNewExampleResponse1().hashCode(), getNewExampleResponse2().hashCode());
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
