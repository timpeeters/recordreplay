package be.xplore.yamlmarshaller;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class YamlMarshaller implements Marshaller {

    private final YAMLFactory yamlFactory = new YAMLFactory();
    private final ObjectMapper mapper = new ObjectMapper(yamlFactory);

    @Override
    public void marshal(Stub stub, Writer writer) throws IOException {
        String yamlString = mapper.writeValueAsString(stub);
        writer.append(yamlString);
    }

    @Override
    public List<Stub> unMarshal(Reader reader) throws IOException {
        List<Stub> stubs = new ArrayList<>();
        MappingIterator<Stub> stubIterator = mapper.readValues(yamlFactory.createParser(reader), Stub.class);
        while (stubIterator.hasNext()) {
            stubs.add(stubIterator.next());
        }
        return stubs;
    }
}
