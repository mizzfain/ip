package kevin;

/**
 * Exception unique to Kevin chatbot.
 */
public class KevinException extends Exception {
    public KevinException(String message) {
        super("FAIL! " + message);
    }
}
