package exceptions;

public class TicketBookingFailedException extends RuntimeException {
    public TicketBookingFailedException() {
        super("Failed to confirm booking");
    }
}
