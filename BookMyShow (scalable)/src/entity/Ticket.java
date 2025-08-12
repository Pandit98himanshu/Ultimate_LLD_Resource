package entity;

import java.time.LocalTime;
import java.util.List;

public class Ticket {
    User user;
    City city;
    CinemaHall hall;
    Show show;
    LocalTime bookingTime;
    List<String> seats;

    public Ticket(User user, City city, CinemaHall hall, Show show, List<String> seats) {
        this.user = user;
        this.city = city;
        this.hall = hall;
        this.show = show;
        bookingTime = null;
        this.seats = seats;
    }

    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void cancel() {
        this.hall.cancelBookedSeats(this.show, this.seats);
    }
}
