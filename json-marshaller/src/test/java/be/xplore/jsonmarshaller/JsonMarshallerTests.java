package be.xplore.jsonmarshaller;


import be.xplore.fakes.model.*;
import be.xplore.fakes.service.Marshaller;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonMarshallerTests {

    private final String expectedJsonString = "{\"" +
            "request\":" +
            "{\"method\":\"GET\"," +
            "\"path\":\"/test\"," +
            "\"queryParams\":" +
            "{\"params\":{}," +
            "\"empty\":true," +
            "\"queryString\":\"\"}," +
            "\"headers\":" +
            "{\"headerMap\":{}," +
            "\"empty\":true}," +
            "\"body\":\"test body\"}," +
            "\"response\":" +
            "{\"statusCode\":200," +
            "\"statusText\":\"OK\"}}";

    private Stub stub;
    private Marshaller marshaller;

    @Before
    public void setUpTest() {
        stub = new Stub(
                Request.Builder.get("/test").queryParams(QueryParams.builder().build())
                        .headers(Headers.builder().build()).body("test body").build(),
                Response.ok()
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
