package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class YamlMarshallerTests {

    private final String expectedYamlString = "---\n" +
            "request:\n" +
            "  method: \"GET\"\n" +
            "  path: \"/test\"\n" +
            "  queryParams: {}\n" +
            "  headers: {}\n" +
            "  body: \"request body\"\n" +
            "response:\n" +
            "  statusCode: 200\n" +
            "  statusText: \"OK\"\n" +
            "  headers: {}\n" +
            "  body: \"response body\"\n";
    private Stub stub;
    private Marshaller marshaller;

    @Before
    public void setUpTest() {
        stub = new Stub(
                Request.Builder.get("/test").queryParams(QueryParams.EMPTY)
                        .headers(Headers.EMPTY).body("request body").build(),
                Response.Builder.ok().body("response body").build()
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

    @Test(expected = UncheckedIOException.class)
    public void marshallerShouldThrowExceptionOnMockWriter() throws IOException {
        try (Writer mockedWriter = mock(Writer.class)) {
            when(mockedWriter.append(anyString())).thenThrow(IOException.class);
            marshaller.marshal(stub, mockedWriter);
        }
    }

    @Test(expected = UncheckedIOException.class)
    public void unmarshallerShouldThrowExceptionOnMockReader() throws IOException {
        try (Reader reader = mock(Reader.class)) {
            when(reader.read(any(char[].class),
                    anyInt(),
                    anyInt())).thenThrow(IOException.class);
            marshaller.unMarshal(reader);
        }
    }

}
