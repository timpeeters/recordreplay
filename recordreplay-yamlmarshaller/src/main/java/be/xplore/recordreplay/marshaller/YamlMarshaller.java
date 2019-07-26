package be.xplore.recordreplay.marshaller;

import be.xplore.recordreplay.model.Stub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;

public class YamlMarshaller implements Marshaller {

    private final ObjectMapper mapper;

    public YamlMarshaller() {
        this.mapper = new ObjectMapper(new YAMLFactory());
    }

    @Override
    public void marshal(Stub stub, Writer writer) {
        try {
            writer.append(mapper.writeValueAsString(new YamlStub(stub)));
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to marshal stub into yaml", e);
        }
    }

    @Override
    public Stub unMarshal(Reader reader) {
        try {
            return mapper.readValue(reader, YamlStub.class).toStub();
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to unmarshal yaml into stub", e);
        }
    }
}
