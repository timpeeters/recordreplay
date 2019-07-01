package be.xplore.demorest.service.exceptions;

import javax.persistence.EntityNotFoundException;

public class UserValidationException extends Exception {

    public UserValidationException(String message) {
        super(message);
    }
    public UserValidationException(Throwable cause) {
        super(cause);
    }
    public UserValidationException(String message, EntityNotFoundException cause) {
        super(message, cause);
    }
}
