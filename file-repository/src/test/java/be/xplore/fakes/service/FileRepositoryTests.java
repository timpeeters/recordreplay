package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.except.InvalidFileException;
import be.xplore.fakes.service.except.InvalidStubException;
import be.xplore.fakes.service.except.MarshalException;
import be.xplore.fakes.service.except.RepositoryException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

public class FileRepositoryTests {

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @Test
    public void createFileRepoFromExistingFile() throws IOException, InvalidFileException, MarshalException {
        new FileRepository<>(tempDir.newFile(), MockedMarshaller.class);
    }

    @Test
    public void createFileRepoFromNonExistingFile() throws IOException, InvalidFileException, MarshalException {
        new FileRepository<>(Paths.get(tempDir.getRoot().getAbsolutePath().concat("FileToCreate")).toFile(),
                MockedMarshaller.class);
    }

    @Test(expected = InvalidFileException.class)
    public void createFileRepoWithNullFileThrows() throws InvalidFileException, MarshalException, IOException {
        new FileRepository<>(null, MockedMarshaller.class);
    }

    @Test(expected = InvalidFileException.class)
    public void createFileRepoFromDirThrows() throws IOException, InvalidFileException, MarshalException {
        new FileRepository<>(tempDir.newFolder(), MockedMarshaller.class);
    }

    @Test(expected = MarshalException.class)
    public void createFileRepoWithInvalidMarshallerThrows() throws IOException, MarshalException, InvalidFileException {
        new FileRepository<>(tempDir.newFile(), InvalidMarshaller.class);
    }

    @Test
    public void addStubToFileRepo() throws MarshalException, InvalidFileException, InvalidStubException,
            RepositoryException, IOException {
        Repository repo = new FileRepository<>(tempDir.newFile(), MockedMarshaller.class);
        repo.add(new Stub());
    }

    @Test
    public void readFromFileRepo() throws MarshalException, InvalidFileException, IOException, RepositoryException {
        Repository repo = new FileRepository<>(tempDir.newFile(), MockedMarshaller.class);
        assertNotNull(repo.find());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = RepositoryException.class)
    public void writeToReadOnlyFileThrows() throws MarshalException, InvalidFileException, IOException,
            RepositoryException, InvalidStubException {
        File file = tempDir.newFile();
        Repository repo = new FileRepository<>(file, MockedMarshaller.class);
        file.setReadOnly();
        repo.add(new Stub());
    }

    @Test(expected = RepositoryException.class)
    public void writeToDeletedFileThrows() throws MarshalException, InvalidFileException, IOException,
            RepositoryException, InvalidStubException {
        Repository repo = new FileRepository<>(tempDir.newFile(), MockedMarshaller.class);
        tempDir.delete();
        repo.add(new Stub());
    }

    @Test(expected = RepositoryException.class)
    public void readFromDeletedFileThrows() throws MarshalException, InvalidFileException, IOException,
            RepositoryException {
        File file = tempDir.newFile();
        Repository repo = new FileRepository<>(file, MockedMarshaller.class);
        tempDir.delete();
        repo.find();
    }
}
