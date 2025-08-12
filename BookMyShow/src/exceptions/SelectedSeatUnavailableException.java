package exceptions;

public class SelectedSeatUnavailableException extends RuntimeException {
    public SelectedSeatUnavailableException(String message) {
        super(message);
    }
}
