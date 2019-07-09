package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class MockedMarshaller implements Marshaller {
    @Override
    public void marshal(Stub stub, Writer writer) throws IOException {
        writer.append(stub.toString());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public Stub unMarshal(Reader reader) throws IOException {
        reader.read();
        return new Stub();
    }
}

