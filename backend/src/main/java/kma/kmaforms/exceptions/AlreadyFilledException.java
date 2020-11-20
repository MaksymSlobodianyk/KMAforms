package kma.kmaforms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyFilledException extends Exception {
    public AlreadyFilledException(String message) {
        super(message);
    }

    public AlreadyFilledException() {
        super("Already filled form");
    }
}
