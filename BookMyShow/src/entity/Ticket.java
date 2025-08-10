package entity;

import java.time.LocalTime;

public class Ticket {
    User user;
    Movie movie;
    City city;
    CinemaHall hall;
    LocalTime startTime;
    LocalTime bookingTime;

    public Ticket(User user, Movie movie, City city, CinemaHall hall, LocalTime startTime) {
        this.user = user;
        this.movie = movie;
        this.city = city;
        this.hall = hall;
        this.startTime = startTime;
        bookingTime = null;
    }

    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
