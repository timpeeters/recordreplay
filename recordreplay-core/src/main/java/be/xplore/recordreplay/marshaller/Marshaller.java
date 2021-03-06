package be.xplore.recordreplay.marshaller;

import be.xplore.recordreplay.model.Stub;

import java.io.Reader;
import java.io.Writer;

public interface Marshaller {
    void marshal(Stub stub, Writer writer);

    Stub unMarshal(Reader reader);
}
