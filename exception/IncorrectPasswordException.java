package exception;

/**
 * Exception thrown when an incorrect password is provided.
 *
 * author Yoshikazu Fujisaka
 */
public class IncorrectPasswordException extends Exception {

    /**
     * Constructor to create an IncorrectPasswordException with a specified message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
