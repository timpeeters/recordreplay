package be.xplore.recordreplay.repository;

import be.xplore.recordreplay.marshaller.Marshaller;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileRepositoryTests {

    private static final Stub STUB = new Stub(Request.Builder.get("").build(), Response.ok());

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @Mock
    private Marshaller mockedMarshaller;

    @Before
    public void mockedMarshallerImplementation() {
        mockMarshal();
        mockUnMarshal();
    }

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

    @SuppressWarnings("checkstyle:executablestatementcount")
    @Test
    public void fileRepoReInitializesCorrectly() throws IOException {
        var repo = createTestFileRepository();
        repo.add(STUB);
        repo = createTestFileRepository();
        tempDir.newFolder();
        assertThat(repo.size()).isEqualTo(1);
        assertThat(repo.find().get(0).getRequest().getMethod()).isEqualTo(STUB.getRequest().getMethod());
    }

    private void mockMarshal() {
        doAnswer(invocation -> {
            Stub stub = invocation.getArgument(0);
            Writer writer = invocation.getArgument(1);
            writer.write(stub.getRequest().getMethod().toString());
            return null;
        }).when(mockedMarshaller).marshal(any(Stub.class), any(Writer.class));
    }

    private void mockUnMarshal() {
        when(mockedMarshaller.unMarshal(any(Reader.class))).thenAnswer(invocation -> {
            BufferedReader reader = new BufferedReader(invocation.getArgument(0));
            return new Stub(Request.builder().method(RequestMethod.valueOf(reader.readLine())).path("").build(),
                    Response.ok());
        });
    }

    private FileRepository createTestFileRepository() {
        return new FileRepository(tempDirPath(), mockedMarshaller);
    }

    private Path tempDirPath() {
        return tempDir.getRoot().toPath();
    }

}
