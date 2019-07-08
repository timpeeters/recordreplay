package be.xplore.jsonmarshaller;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Marshaller;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class JsonMarshaller implements Marshaller {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void marshal(Stub stub, Writer writer) throws IOException {
        String jsonString = mapper.writeValueAsString(stub);
        writer.append(jsonString);
    }

    @Override
    public List<Stub> unMarshal(Reader reader) throws IOException {
        List<Stub> stubs = new ArrayList<>();
        MappingIterator<Stub> stubIterator = mapper.readValues(new JsonFactory().createParser(reader), Stub.class);
        while (stubIterator.hasNext()) {
            stubs.add(stubIterator.next());
        }
        return stubs;
    }

}
