package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(repo.size()).isEqualTo(1);
    }

    @Test
    public void readStubsFromFileRepo() {
        var repo = createTestFileRepository();
        repo.add(new Stub());
        var result = repo.find();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
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

    @Test
    public void readWhenRepoHasSubDirNoThrow() throws IOException {
        Repository repo = createTestFileRepository();
        tempDir.newFolder();
        repo.find();
    }

    private FileRepository<MockedMarshaller> createTestFileRepository() {
        return new FileRepository<>(tempDirPath(), MockedMarshaller.class);
    }

    private Path tempDirPath() {
        return tempDir.getRoot().toPath();
    }
}
