package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface Marshaller {
    void marshal(Stub stub, Writer writer) throws IOException;

    Stub unMarshal(Reader reader);
}
