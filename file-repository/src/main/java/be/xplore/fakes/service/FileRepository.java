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
        this.marshaller = constructMarshaller(marshallerType);
        this.targetDir = targetDir;
    }

    @Override
    public void add(Stub stub) {
     /*   try (Writer w = Files.newBufferedWriter(Paths.get(targetDir, ""))) {
            marshaller.marshal(stub, w);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }*/
    }

    @Override
    public List<Stub> find() {
      /*  try (Reader r = Files.newBufferedReader(file.toPath())) {
            return marshaller.unMarshal(r);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }*/
        return Collections.emptyList();
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

    private static String getUniqueStubFileName() {
        return UUID.randomUUID().toString();
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
