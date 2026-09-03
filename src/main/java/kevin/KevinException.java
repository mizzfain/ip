package kevin;

public class KevinException extends Exception {
    public KevinException(String message) {
        super("FAIL! " + message);
    }
}
