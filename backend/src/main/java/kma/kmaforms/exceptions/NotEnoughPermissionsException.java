package kma.kmaforms.exceptions;

public class NotEnoughPermissionsException extends Exception {
    public NotEnoughPermissionsException(String message) {
        super(message);
    }

    public NotEnoughPermissionsException() {
        super("Not Enough Permissions");
    }
}
