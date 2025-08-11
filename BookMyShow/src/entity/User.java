package entity;

import services.MovieBookingService;
import services.PaymentService;

import java.time.LocalTime;
import java.util.List;

public class User {
    private String name;
    private Ticket ticket;
    private MovieBookingService bookingService;

    public User(String name) {
        this.name = name;
        this.ticket = null;
        bookingService = new MovieBookingService();
    }

    public void bookMovieTicket(Movie movie, LocalTime startTime, City city, CinemaHall hall, List<String> seats) {
        try {
            this.ticket = bookingService.bookMovie(this, movie, startTime, city, hall, seats);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makePayment() {
        boolean retv = false;
        new Thread(() -> {
            new PaymentService().payForTicket(this.ticket);
        }).start();
        if (retv) {
            this.ticket.setBookingTime(LocalTime.now());
        }
    }
}
