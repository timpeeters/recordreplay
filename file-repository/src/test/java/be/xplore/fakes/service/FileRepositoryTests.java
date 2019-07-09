package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void addStubToFileRepo() {
        var repo = createTestFileRepository();
        repo.add(new Stub());
        assertEquals(1, repo.size());
    }

    @Test
    public void readStubsFromFileRepo() {
        var repo = createTestFileRepository();
        repo.add(new Stub());
        var result = repo.find();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test(expected = UncheckedIOException.class)
    public void writeToDeletedFileThrows() {
        Repository repo = createTestFileRepository();
        tempDir.delete();
        repo.add(new Stub());
    }

    @Test(expected = UncheckedIOException.class)
    public void readFromDeletedFileThrows() {
        Repository repo = createTestFileRepository();
        tempDir.delete();
        repo.find();
    }

    private FileRepository<MockedMarshaller> createTestFileRepository() {
        return new FileRepository<>(tempDirPath(), MockedMarshaller.class);
    }

    private Path tempDirPath() {
        return tempDir.getRoot().toPath();
    }
}
