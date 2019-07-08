package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class YamlMarshallerTests {


    private final String expectedYamlString = "---\n" +
            "request:\n" +
            "  method: \"GET\"\n" +
            "  path: \"/test\"\n" +
            "response:\n" +
            "  statusCode: 200\n" +
            "  statusText: \"Successful\"\n";

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

        marshaller = new YamlMarshaller();
    }

    @Test
    public void marshallWritesYamlString() throws IOException {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(stub, stringWriter);
        assertEquals("Yaml string marshalled correctly", expectedYamlString, stringWriter.toString());
    }

    @Test
    public void unmarshallCreatesStubFromYaml() throws IOException {
        Stub stubFromYaml = marshaller.unMarshal(new StringReader(expectedYamlString)).get(0);
        assertEquals("No correct stub unmarshalled", stub, stubFromYaml);
    }

}
