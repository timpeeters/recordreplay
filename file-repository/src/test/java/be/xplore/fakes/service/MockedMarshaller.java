package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

public class MockedMarshaller implements Marshaller {
    @Override
    public void marshal(Stub stub, Writer writer) throws IOException {
        writer.append(stub.toString());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public List<Stub>unMarshal(Reader reader) throws IOException {
        reader.read();
        return Collections.singletonList(new Stub());
    }
}

