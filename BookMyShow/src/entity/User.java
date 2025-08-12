package entity;

import exceptions.InvalidTicketException;
import java.time.LocalTime;
import java.util.List;
import services.MovieBookingService;
import services.PaymentService;

public class User {
    private String name;
    private Ticket ticket;
    private MovieBookingService bookingService;

    public User(String name) {
        this.name = name;
        this.ticket = null;
        bookingService = new MovieBookingService();
    }

    public void bookMovieTicket(City city, Show show, List<String> seats) {
        try {
            this.ticket = bookingService.bookMovie(this, city, show, seats);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makePayment() {
        if (this.ticket == null) {
            throw new InvalidTicketException();
        }
        
        PaymentService paymentService = new PaymentService();
        boolean paymentSuccess = paymentService.payForTicket(this.ticket);
        
        if (paymentSuccess) {
            this.ticket.setBookingTime(LocalTime.now());
        }
    }

    public void cancelBooking() {
        if (this.ticket != null) {
            this.ticket.cancel();
            this.ticket = null;
        }
    }
}
