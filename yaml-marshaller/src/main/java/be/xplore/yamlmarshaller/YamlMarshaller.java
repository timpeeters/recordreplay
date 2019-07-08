package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class YamlMarshaller implements Marshaller {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @Override
    public void marshal(Stub stub, Writer writer) throws IOException {
        String yamlString = mapper.writeValueAsString(stub);
        writer.append(yamlString);
        writer.close();
    }

    @Override
    public Stub unMarshal(Reader reader) throws IOException {
        return mapper.readValue(reader, Stub.class);
    }
}
