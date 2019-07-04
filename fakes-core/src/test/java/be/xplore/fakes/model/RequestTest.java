package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    @Test
    public void checkEquality(){
        Request request1 = new Request()
                .setPath("/test")
                .setMethod(RequestMethod.GET);

        Request request2 = new Request()
                .setPath("/test")
                .setMethod(RequestMethod.GET);

        assertEquals("Equals method not implemented correctly",request1, request2);
    }

}
