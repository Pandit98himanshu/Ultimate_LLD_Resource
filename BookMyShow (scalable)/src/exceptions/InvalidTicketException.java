package exceptions;

public class InvalidTicketException extends RuntimeException {
    public InvalidTicketException() {
        super("Invalid Ticket");
    }
}
