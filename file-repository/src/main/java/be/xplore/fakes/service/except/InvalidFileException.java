package be.xplore.fakes.service.except;

import java.io.File;

public class InvalidFileException extends Exception {
    private static final long serialVersionUID = 4905724771843849711L;

    public InvalidFileException(String message, File file) {
        super(String.format("%s (Path: %s)", message, file == null ? "null" : file.getAbsolutePath()));
    }
}
