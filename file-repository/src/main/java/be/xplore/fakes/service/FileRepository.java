package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FileRepository<M extends Marshaller> implements Repository {
    private final Path targetDir;
    private final Marshaller marshaller;

    public FileRepository(Path targetDir, Class<M> marshallerType) {
        if (!validatePath(targetDir)) {
            createDir(targetDir);
        }
        this.targetDir = targetDir.toAbsolutePath();
        this.marshaller = constructMarshaller(marshallerType);
    }

    @Override
    public void add(Stub stub) {
        try (Writer w = Files.newBufferedWriter(Paths.get(targetDir.toString(), getUniqueStubFileName(stub)))) {
            marshaller.marshal(stub, w);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<Stub> find() {
        /*
        Stream<Path> dirContents;
        try {
            dirContents = Files.list(targetDir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }


        try (Reader r = Files.newBufferedReader()) {
            return marshaller.unMarshal();
        } catch (IOException e) {
            throw new RepositoryException(e);

        }*/
        return Collections.emptyList();
    }

    public int count() {
        try {
            return (int) Files.list(targetDir).count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static boolean validatePath(Path path) {
        if (!Files.exists(path)) {
            return false;
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException(String.format("Path(%s) is not a directory!", path.toString()));
        }
        return true;
    }

    private static String getUniqueStubFileName(Stub stub) {
        var req = stub.getRequest();
        return req.getMethod().toString() + "_" +
                req.getPath() +
                UUID.randomUUID().toString();
    }

    private static void createDir(Path dir) {
        try {
            Files.createDirectories(dir.toAbsolutePath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Marshaller constructMarshaller(Class<? extends Marshaller> marshallerType) {
        try {
            return marshallerType.getConstructor().newInstance();
        } catch (InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Can not instantiate Marshaller of type %s, does it have" +
                    " a public default constructor?", marshallerType.getName()), e);
        }
    }
}
