package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class FileRepositoryTests {

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @SuppressWarnings("unused")
    @Test
    public void createFileRepoFromPath() {
        Repository r = createTestFileRepository();
    }

    @Test
    public void createFileRepoFromNonExistingDir() throws IOException {
        Path deletedDir = tempDir.newFolder().toPath();
        tempDir.delete();
        new FileRepository<>(deletedDir, MockedMarshaller.class);
    }

    @Test(expected = NullPointerException.class)
    public void createFileRepoWithNullFileThrowsNullptr() {
        new FileRepository<>(null, MockedMarshaller.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFileRepoFromFilePathThrowsIllegalArgument() throws IOException {
        new FileRepository<>(tempDir.newFile().toPath(), MockedMarshaller.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFileRepoWithInvalidMarshallerThrowsIllegalArgument() {
        new FileRepository<>(tempDirPath(), InvalidMarshaller.class);
    }
/*
    @Test
    public void addStubToFileRepo() throws IOException {
        FileRepository repo = createTestFileRepository();
        repo.add(new Stub());
        assertEquals(1, Files.list(tempDirPath()).count());
    }
/*
    @Test
    public void readStubsFromFileRepo() throws MarshalException, InvalidPathException, IOException,
            RepositoryException {
        Repository repo = createTestFileRepository();
        assertNotNull(repo.find());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = RepositoryException.class)
    public void writeToReadOnlyFileThrows() throws MarshalException, InvalidPathException, IOException,
            RepositoryException, InvalidStubException {
        File file = tempDir.newFile();
        Repository repo = new FileRepository<>(file, MockedMarshaller.class);
        file.setReadOnly();
        repo.add(new Stub());
    }

    @Test(expected = RepositoryException.class)
    public void writeToDeletedFileThrows() throws MarshalException, InvalidPathException, IOException,
            RepositoryException, InvalidStubException {
        Repository repo = createTestFileRepository();
        tempDir.delete();
        repo.add(new Stub());
    }

    @Test(expected = RepositoryException.class)
    public void readFromDeletedFileThrows() throws MarshalException, InvalidPathException, IOException,
            RepositoryException {
        Repository repo = createTestFileRepository();
        tempDir.delete();
        repo.find();
    }*/

    private FileRepository<MockedMarshaller> createTestFileRepository() {
        return new FileRepository<>(tempDirPath(), MockedMarshaller.class);
    }

    private Path tempDirPath() {
        return tempDir.getRoot().toPath();
    }
}
