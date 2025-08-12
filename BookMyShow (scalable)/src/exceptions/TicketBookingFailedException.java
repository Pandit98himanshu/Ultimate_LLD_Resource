package exceptions;

public class TicketBookingFailedException extends RuntimeException {
    public TicketBookingFailedException() {
        super("Ticket booking failed");
    }
}
