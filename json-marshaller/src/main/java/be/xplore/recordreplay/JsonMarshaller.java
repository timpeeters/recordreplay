package be.xplore.recordreplay;

import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repo.Marshaller;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;


public class JsonMarshaller implements Marshaller {

    private final ObjectMapper mapper;

    public JsonMarshaller() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void marshal(Stub stub, Writer writer) {
        try {
            writer.append(mapper.writeValueAsString(new JsonStub(stub)));
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to marshal stub into json", e);
        }
    }

    @Override
    public Stub unMarshal(Reader reader) {
        try {
            return mapper.readValue(reader, JsonStub.class).toStub();
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to unmarshal json into stub", e);
        }
    }

}
