package be.xplore.fakes.service.except;

public class InvalidStubException extends RuntimeException {
    public InvalidStubException(String message) {
        super(message);
    }

    public InvalidStubException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStubException(Throwable cause) {
        super(cause);
    }
}
