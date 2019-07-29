package be.xplore.recordreplay.repo;

import be.xplore.recordreplay.model.Stub;
import java.io.Reader;
import java.io.Writer;

@SuppressWarnings("unused")
public class InconstructibleMarshaller implements Marshaller {

    public InconstructibleMarshaller(int i) {
        throw new IllegalArgumentException();
    }

    @Override
    public void marshal(Stub stub, Writer writer) {

    }

    @Override
    public Stub unMarshal(Reader reader) {
        return null;
    }
}