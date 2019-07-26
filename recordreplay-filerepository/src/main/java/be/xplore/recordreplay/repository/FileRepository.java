package be.xplore.recordreplay.repository;

import be.xplore.recordreplay.marshaller.Marshaller;
import be.xplore.recordreplay.model.Stub;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class FileRepository implements Repository {
    private final Path targetDir;
    private final Marshaller marshaller;
    private int size;

    public FileRepository(Path targetDir, Marshaller marshaller) {
        if (!validatePath(targetDir)) {
            createDir(targetDir);
        }
        this.targetDir = targetDir.toAbsolutePath();
        this.marshaller = marshaller;
        this.size = 0;
    }

    @Override
    public void add(Stub stub) {
        OpenOption[] options = {StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING};
        try (Writer w = Files
                .newBufferedWriter(Paths.get(targetDir.toString(), getUniqueStubFileName(stub)), options)) {
            marshaller.marshal(stub, w);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        ++size;
    }

    @Override
    public List<Stub> find() {
        List<Stub> results = new ArrayList<>(size());
        contentStream().forEach(path -> {
            if (Files.isRegularFile(path)) {
                results.add(read(path));
            }
        });
        return results;
    }

    public int size() {
        return size;
    }

    private Stub read(Path file) {
        try (Reader r = Files.newBufferedReader(file)) {
            return marshaller.unMarshal(r);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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

    private Stream<Path> contentStream() {
        try {
            return Files.list(targetDir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String getUniqueStubFileName(Stub stub) {
        var req = stub.getRequest();
        return req.getMethod().toString() + "_" +
                req.getPath().replace('/', '_') +
                UUID.randomUUID().toString();
    }

    private static void createDir(Path dir) {
        try {
            Files.createDirectories(dir.toAbsolutePath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
