package be.xplore.jsonmarshaller;


import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonMarshallerTests {

    private Path filePath;

    private final Request request = new Request()
            .setMethod(RequestMethod.GET)
            .setPath("/test");
    private final Response response = new Response()
            .setStatusCode(200)
            .setStatusText("Successful");

    private final Stub stub = new Stub()
            .setRequest(request)
            .setResponse(response);

    @Test
    public void marshallCreatesFile() throws IOException {
        Marshaller marshaller = new JsonMarshaller();
        marshaller.marshal(stub, Files.newBufferedWriter(filePath));
        assertTrue("File doesn't exist", Files.exists(filePath));
    }

    @Test
    public void marshallWritesJsonString() throws IOException {
        String stubJson = new ObjectMapper()
                .writeValueAsString(stub);
        Marshaller marshaller = new JsonMarshaller();
        marshaller.marshal(stub, Files.newBufferedWriter(filePath));
        assertEquals("Json string not written to file correctly", Files.readString(filePath), stubJson);
    }

    @Test
    public void unmarshallCreatesStubFromJson() throws IOException {
        Marshaller marshaller = new JsonMarshaller();
        marshaller.marshal(stub, Files.newBufferedWriter(filePath));
        Stub stubFromJson = marshaller.unMarshal(Files.newBufferedReader(filePath));
        assertEquals("No correct stub generated from json", stub, stubFromJson);
    }

    @Before
    public void createTestFile() throws IOException {
        filePath = Files.createTempFile("testJson", ".txt");
    }
    @After
    public void deleteTestFile() throws IOException {
        Files.deleteIfExists(filePath);
    }

}
