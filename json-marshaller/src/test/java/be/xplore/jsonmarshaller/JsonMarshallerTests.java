package be.xplore.jsonmarshaller;


import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;


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
}
