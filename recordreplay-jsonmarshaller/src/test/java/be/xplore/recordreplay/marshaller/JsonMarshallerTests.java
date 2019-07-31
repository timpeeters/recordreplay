package be.xplore.recordreplay.marshaller;


import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.QueryParams;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
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
public class JsonMarshallerTests {

    private final String expectedJsonString =
            "{\"request\":{\"method\":\"GET\",\"path\":\"/test\",\"queryParams\":{}," +
                    "\"headers\":{},\"body\":\"request body\"},\"response\":{\"statusCode\":200," +
                    "\"statusText\":\"OK\",\"headers\":{},\"body\":\"response body\"}}";

    private Stub stub;
    private Marshaller marshaller;


    @Before
    public void setUpTest() {
        stub = new Stub(
                Request.Builder.get("/test").queryParams(QueryParams.EMPTY)
                        .headers(Headers.EMPTY).body("request body").build(),
                Response.Builder.ok().body("response body").build()
        );
        marshaller = new JsonMarshaller();
    }


    @Test
    public void marshallWritesJsonString() {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(stub, stringWriter);
        assertThat(stringWriter.toString()).as("Json string marshalled correctly").isEqualTo(expectedJsonString);
    }

    @Test
    public void unmarshallCreatesStubFromJson() {
        Stub unmarshalledStub = marshaller.unMarshal(new StringReader(expectedJsonString));
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
                    anyInt()))
                    .thenThrow(IOException.class);
            marshaller.unMarshal(reader);
        }
    }

    @Test
    public void foundByLocator() {
        Marshaller m = new MarshallerLocator().load();
        assertThat(m).isExactlyInstanceOf(JsonMarshaller.class);
    }

}
