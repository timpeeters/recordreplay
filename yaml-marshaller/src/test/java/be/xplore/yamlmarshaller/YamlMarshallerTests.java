package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class YamlMarshallerTests {

    private Stub stub;
    private String stubYaml;
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

        stubYaml = new ObjectMapper(new YAMLFactory())
                .writeValueAsString(stub);

        marshaller = new YamlMarshaller();
    }

    @Test
    public void marshallWritesJsonString() throws IOException {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(stub, stringWriter);
        assertEquals("Yaml string marshalled correctly", stringWriter.toString(), stubYaml);
    }

    @Test
    public void unmarshallCreatesStubFromJson() throws IOException {
        Stub stubFromYaml = marshaller.unMarshal(new StringReader(stubYaml));
        assertEquals("No correct stub unmarshalled", stub, stubFromYaml);
    }

}
