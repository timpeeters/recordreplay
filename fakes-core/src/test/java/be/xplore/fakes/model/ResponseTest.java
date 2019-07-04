package be.xplore.fakes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void checkEquality(){
        Response response1 = new Response()
                .setStatusCode(200)
                .setStatusText("OK");

        Response response2 = new Response()
                .setStatusCode(200)
                .setStatusText("OK");

        assertEquals("Equals method not implemented correctly", response1, response2);

    }

}
