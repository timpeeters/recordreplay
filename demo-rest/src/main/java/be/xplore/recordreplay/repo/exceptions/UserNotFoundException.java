package be.xplore.recordreplay.repo.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5760483775721880456L;

    public UserNotFoundException(long id) {
        super(String.format("User with id %d does not exist", id));
    }
}
