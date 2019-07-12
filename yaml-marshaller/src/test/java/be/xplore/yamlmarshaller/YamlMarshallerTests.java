package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class YamlMarshallerTests {

    private final String expectedYamlString = "---\n" +
            "request:\n  " +
            "method: \"GET\"\n  " +
            "path: \"/test\"\n  " +
            "queryParams: []\n  " +
            "headers: []\n  " +
            "body: \"test body\"\n" +
            "response:\n  " +
            "statusCode: 200\n  " +
            "statusText: \"OK\"\n";
    private Stub stub;
    private Marshaller marshaller;

    @Before
    public void setUpTest() {
        stub = new Stub(
                Request.Builder.get("/test").queryParams(new ArrayList<>())
                        .headers(new ArrayList<>()).body("test body").build(),
                Response.ok()
        );
        marshaller = new YamlMarshaller();
    }

    @Test
    public void marshallWritesYamlString() {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(stub, stringWriter);
        assertThat(stringWriter.toString()).as("Yaml string not marshalled correctly").isEqualTo(expectedYamlString);
    }

    @Test
    public void unmarshallCreatesStubFromYamlString() {
        Stub unmarshalledStub = marshaller.unMarshal(new StringReader(expectedYamlString));
        assertThat(unmarshalledStub).as("No correct stub unmarshalled").isEqualTo(stub);
    }

}
