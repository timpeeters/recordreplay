package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;

public class MockedMarshaller implements Marshaller {
    @Override
    public void marshal(Stub stub, Writer writer) {
        try {
            writer.append(stub.toString());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public Stub unMarshal(Reader reader) {
        try {
            reader.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new Stub();
    }
}

