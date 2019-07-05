package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.except.InvalidFileException;
import be.xplore.fakes.service.except.InvalidStubException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileRepository implements Repository {
    private final File file;

    public FileRepository(File file) throws IOException, InvalidFileException {
        if (!file.exists()) {
            Files.createFile(Paths.get(file.getAbsolutePath()));
        }
        this.file = file;
        validateFile();
    }

    @Override
    public void add(Stub stub) throws InvalidStubException {

    }

    @Override
    public List<Stub> find() {
        return null;
    }

    private void validateFile() throws InvalidFileException {
        if (!file.isFile()) {
            throw new InvalidFileException("File is a directory", file);
        }
    }
}
