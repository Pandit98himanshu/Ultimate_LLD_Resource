package exceptions;

public class SelectedSeatUnavailableException extends RuntimeException {
    public SelectedSeatUnavailableException() {
        super("Selected seats are not available");
    }
}
