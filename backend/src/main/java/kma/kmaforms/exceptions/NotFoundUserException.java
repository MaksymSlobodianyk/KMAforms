package kma.kmaforms.exceptions;

public class NotFoundUserException extends Exception {
    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException() {
        super("Not Found");
    }
}
