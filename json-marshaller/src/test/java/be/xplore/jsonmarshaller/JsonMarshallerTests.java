package be.xplore.jsonmarshaller;


import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;


public class JsonMarshallerTests {

    private Stub stub;
    private String stubJson;
    private Marshaller marshaller;

    @Before
    public void setUpTest() throws JsonProcessingException {
        Request request = new Request()
                .setMethod(RequestMethod.GET)
                .setPath("/test");
        Response response = new Response()
                .setStatusCode(200)
                .setStatusText("Successful");
        stub = new Stub()
                .setRequest(request)
                .setResponse(response);

        stubJson = new ObjectMapper().writeValueAsString(stub);

        marshaller = new JsonMarshaller();
    }


    @Test
    public void marshallWritesJsonString() throws IOException {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(stub, stringWriter);
        assertEquals("Json string marshalled correctly", stringWriter.toString(), stubJson);
    }

    @Test
    public void unmarshallCreatesStubFromJson() throws IOException {
        Stub stubFromJson = marshaller.unMarshal(Files.newBufferedReader(filePath));
        assertEquals("No correct stub unmarshalled", stub, stubFromJson);
    }

}
