package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Stub;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;

public class BrokenMarshaller implements Marshaller {

    @Override
    public void marshal(Stub stub, Writer writer) {
        throw new UncheckedIOException(new IOException());
    }

    @Override
    public Stub unMarshal(Reader reader) {
        throw new UncheckedIOException(new IOException());
    }
}
