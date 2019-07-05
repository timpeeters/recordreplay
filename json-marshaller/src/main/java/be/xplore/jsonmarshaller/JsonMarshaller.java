package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonMarshaller implements Marshaller {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void marshal(Stub stub, Writer writer) throws IOException {
        String jsonString = mapper.writeValueAsString(stub);
        writer.append(jsonString);
        writer.close();
    }

    @Override
    public Stub unMarshal(Reader reader) throws IOException {
        return mapper.readValue(reader, Stub.class);
    }

}
