package be.xplore.recordreplay.matcher;

public class NoExactMatchFoundException extends RuntimeException {

    private static final long serialVersionUID = 1095368681607087486L;

    public NoExactMatchFoundException(String message) {
        super(message);
    }

}
