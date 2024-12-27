package exception;

/**
 * Exception thrown when an invalid format is encountered.
 *
 * author Yoshikazu Fujisaka
 */
public class InvalidFormatException extends Exception {

    /**
     * Constructor to create an InvalidFormatException with a specified message.
     *
     * @param message The message to be displayed when the exception is thrown.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
