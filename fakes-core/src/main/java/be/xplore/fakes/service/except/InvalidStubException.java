package be.xplore.fakes.service.except;

public class InvalidStubException extends RuntimeException {
    private static final long serialVersionUID = -7171773124446269017L;

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
