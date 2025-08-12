package exceptions;

public class TicketBookingFailedException extends RuntimeException {
    public TicketBookingFailedException(String message) {
        super(message);
    }
}
