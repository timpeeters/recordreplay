package be.xplore.recordreplay.repo;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.service.Repository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FileRepositoryTests {

    private static final Stub STUB = new Stub(Request.Builder.get("").build(), Response.ok());

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @Mock
    private Marshaller mockedMarshaller;

    @SuppressWarnings("unused")
    @Test
    public void createFileRepoFromPath() {
        Repository r = createTestFileRepository();
    }

    @Test
    public void createFileRepoFromNonExistingDir() throws IOException {
        Path deletedDir = tempDir.newFolder().toPath();
        tempDir.delete();
        new FileRepository(deletedDir, mockedMarshaller);
    }

    @Test(expected = NullPointerException.class)
    public void createFileRepoWithNullPathThrowsNullptr() {
        new FileRepository(null, mockedMarshaller);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFileRepoFromFilePathThrowsIllegalArgument() throws IOException {
        new FileRepository(tempDir.newFile().toPath(), mockedMarshaller);
    }

    @Test
    public void addStubToFileRepo() {
        var repo = createTestFileRepository();
        repo.add(STUB);
        assertThat(repo.size()).isEqualTo(1);
    }

    @Test
    public void readStubsFromFileRepo() {
        FileRepository repo = createTestFileRepository();
        repo.add(STUB);
        java.util.List<Stub> result = repo.find();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test(expected = UncheckedIOException.class)
    public void writeToDeletedFileThrows() {
        Repository repo = createTestFileRepository();
        tempDir.delete();
        repo.add(STUB);
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

    @Test(expected = UncheckedIOException.class)
    public void marshallingFailureThrows() {
        new FileRepository(tempDirPath(), new BrokenMarshaller()).add(STUB);
    }

    @Test(expected = UncheckedIOException.class)
    public void unmarshallingFailureThrows() {
        var repo = new FileRepository(tempDirPath(), new BrokenMarshaller());
        repo.add(STUB);
        repo.find();
    }

    @Test(expected = IllegalArgumentException.class)
    public void marshallerNoDefConstructor() {
        new FileRepository(tempDirPath(), new InconstructibleMarshaller(1));
    }

    private FileRepository createTestFileRepository() {
        return new FileRepository(tempDirPath(), mockedMarshaller);
    }

    private Path tempDirPath() {
        return tempDir.getRoot().toPath();
    }

}