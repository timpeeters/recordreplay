package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StubTest {

    private final Response response1 = new Response()
            .setStatusCode(200)
            .setStatusText("OK");

    private final Response response2 = new Response()
            .setStatusCode(200)
            .setStatusText("OK");

    private final Request request1 = new Request()
            .setPath("/test")
            .setMethod(RequestMethod.GET);

    private final Request request2 = new Request()
            .setPath("/test")
            .setMethod(RequestMethod.GET);

    private final Stub stub1 = new Stub()
            .setResponse(response1)
            .setRequest(request1);

    private final Stub stub2 = new Stub()
            .setResponse(response2)
            .setRequest(request2);

    @Test
    public void checkEquality(){
        assertEquals("Equals method not implemented correctly",stub1,stub2);
    }

}
