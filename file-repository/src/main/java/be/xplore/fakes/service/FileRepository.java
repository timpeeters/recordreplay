package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.except.InvalidFileException;
import be.xplore.fakes.service.except.MarshalException;
import be.xplore.fakes.service.except.RepositoryException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileRepository<M extends Marshaller> implements Repository {
    private final File file;
    private final Marshaller marshaller;

    public FileRepository(File file, Class<M> marshallerType)
            throws InvalidFileException, IOException, MarshalException {
        this.file = file;
        createFileIfNotExists();
        validateFile();
        try {
            this.marshaller = marshallerType.getConstructor().newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            throw new MarshalException(e);
        }
    }

    @Override
    public void add(Stub stub) throws RepositoryException {
        try (Writer w = Files.newBufferedWriter(file.toPath(), StandardOpenOption.APPEND)) {
            marshaller.marshal(stub, w);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Stub> find() throws RepositoryException {
        try (Reader r = Files.newBufferedReader(file.toPath())) {
            return null;
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    private void validateFile() throws InvalidFileException {
        if (file.isDirectory()) {
            throw new InvalidFileException("File is a directory", file);
        }
    }

    private void createFileIfNotExists() throws IOException, InvalidFileException {
        if (file == null) {
            throw new InvalidFileException("File is null!");
        }
        if (!file.exists()) {
            Files.createFile(Paths.get(file.getAbsolutePath()));
        }
    }
}
