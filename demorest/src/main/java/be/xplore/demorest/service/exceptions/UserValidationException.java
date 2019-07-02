package be.xplore.demorest.service.exceptions;

import javax.persistence.EntityNotFoundException;

public class UserValidationException extends Exception {

    public UserValidationException(String message) {
        super(message);
    }
}
