package be.xplore.jsonmarshaller;


import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;


public class JsonMarshallerTests {

    private final String expectedJsonString = "{\"request\":{\"method\":\"GET\",\"path\":\"/test\"}" +
            ",\"response\":{\"statusCode\":200,\"statusText\":\"Successful\"}}";
    private Stub stub;
    private Marshaller marshaller;

    @Before
    public void setUpTest() {
        Request request = new Request()
                .setMethod(RequestMethod.GET)
                .setPath("/test");
        Response response = new Response()
                .setStatusCode(200)
                .setStatusText("Successful");
        stub = new Stub()
                .setRequest(request)
                .setResponse(response);

        marshaller = new JsonMarshaller();
    }


    @Test
    public void marshallWritesJsonString() throws IOException {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(stub, stringWriter);
        assertEquals("Json string marshalled correctly", expectedJsonString, stringWriter.toString());
    }

    @Test
    public void unmarshallCreatesStubFromJson() throws IOException {
        List<Stub> stubs = marshaller.unMarshal(new StringReader(expectedJsonString));
        assertEquals("No correct stub unmarshalled", stub, stubs.get(0));
    }
}
