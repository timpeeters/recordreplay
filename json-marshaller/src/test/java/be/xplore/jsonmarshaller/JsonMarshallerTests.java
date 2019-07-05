package be.xplore.jsonmarshaller;


import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonMarshallerTests {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    private Path jsonPath;
    private Stub stub;

    @Before
    public void createTestFile() throws IOException {
        jsonPath = folder.newFile("jsonStub.txt").toPath();
    }

    @Before
    public void createTestStub() {
        Request request = new Request()
                .setMethod(RequestMethod.GET)
                .setPath("/test");
        Response response = new Response()
                .setStatusCode(200)
                .setStatusText("Successful");
        stub = new Stub()
                .setRequest(request)
                .setResponse(response);
    }

    @Test
    public void marshallCreatesFile() throws IOException {
        Marshaller marshaller = new JsonMarshaller();
        marshaller.marshal(stub, Files.newBufferedWriter(jsonPath));
        assertTrue("File doesn't exist", Files.exists(jsonPath));
    }

    @Test
    public void marshallWritesJsonString() throws IOException {
        String stubJson = new ObjectMapper()
                .writeValueAsString(stub);
        Marshaller marshaller = new JsonMarshaller();
        marshaller.marshal(stub, Files.newBufferedWriter(jsonPath));
        assertEquals("Json string not written to file correctly", Files.readString(jsonPath), stubJson);
    }

    @Test
    public void unmarshallCreatesStubFromJson() throws IOException {
        Marshaller marshaller = new JsonMarshaller();
        marshaller.marshal(stub, Files.newBufferedWriter(jsonPath));
        Stub stubFromJson = marshaller.unMarshal(Files.newBufferedReader(jsonPath));
        assertEquals("No correct stub generated from json", stub, stubFromJson);
    }

}
