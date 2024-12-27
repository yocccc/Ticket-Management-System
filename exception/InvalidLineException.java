package exception;

/**
 * Exception thrown when an invalid line is encountered.
 *
 * author Yoshikazu Fujisaka
 */
public class InvalidLineException extends Exception {
    /**
     * Constructor to create an InvalidLineException with a specified message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public InvalidLineException(String message) {
        super(message);
    }
}
