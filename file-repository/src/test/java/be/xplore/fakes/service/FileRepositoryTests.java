package be.xplore.fakes.service;

import be.xplore.fakes.service.except.InvalidFileException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Paths;

public class FileRepositoryTests {
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @Test
    public void createFileRepoFromExistingFile() throws IOException, InvalidFileException {
        new FileRepository(tempDir.newFile());
    }

    @Test
    public void createFileRepoFromNonExistingFile() throws IOException, InvalidFileException {
        new FileRepository(Paths.get(tempDir.getRoot().getAbsolutePath().concat("FileToCreate")).toFile());
    }

    @Test(expected = InvalidFileException.class)
    public void createFileRepoFromDirShouldThrow() throws IOException, InvalidFileException {
        new FileRepository(tempDir.newFolder());
    }

}
