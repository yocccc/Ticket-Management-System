package exception;

/**
 * Exception thrown when there is no a file.
 *
 * author Yoshikazu Fujisaka
 */
public class NotFoundException extends Exception {
    /**
     * Constructor to create an NotFoundException with a specified message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
