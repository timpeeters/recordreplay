package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ResponseTest {

    @Test
    public void checkEquality() {
        Response response1 = getNewExampleResponse();
        Response response2 = getNewExampleResponse();
        assertEquals(response1, response1);
        assertEquals("Equals method not implemented correctly", response1, response2);
    }

    @Test
    public void checkNotEqual() {
        Response response1 = getNewExampleResponse();
        Response response2 = new Response()
                .setStatusCode(404)
                .setStatusText("NOT FOUND");

        assertNotEquals(response1, response2);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkNullAndOtherClass() {
        Response response1 = getNewExampleResponse();
        Response response2 = null;

        assertNotEquals(response1, response2);
        assertNotEquals(response1, 5);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(getNewExampleResponse().hashCode(), getNewExampleResponse().hashCode());
        assertNotEquals(getNewExampleResponse().hashCode(), getNewExampleResponse().setStatusCode(404).hashCode());
        assertNotEquals(getNewExampleResponse().hashCode(), getNewExampleResponse().setStatusText("abc").hashCode());
    }

    private Response getNewExampleResponse() {
        return new Response()
                .setStatusCode(200)
                .setStatusText("OK");
    }

}
