package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.Reader;
import java.io.Writer;

@SuppressWarnings("unused")
public class InconstructibleMarshaller implements Marshaller {

    public InconstructibleMarshaller(int i) {
    }

    @Override
    public void marshal(Stub stub, Writer writer) {

    }

    @Override
    public Stub unMarshal(Reader reader) {
        return null;
    }
}
