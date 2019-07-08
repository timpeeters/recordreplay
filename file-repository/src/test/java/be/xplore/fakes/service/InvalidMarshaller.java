package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

@SuppressWarnings("unused")
public class InvalidMarshaller implements Marshaller {

    public InvalidMarshaller(int i) {
    }

    @Override
    public void marshal(Stub stub, Writer writer) {

    }

    @Override
    public List<Stub> unMarshal(Reader reader) {
        return null;
    }
}
