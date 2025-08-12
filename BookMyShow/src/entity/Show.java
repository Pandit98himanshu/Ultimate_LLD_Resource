package entity;

import java.time.LocalTime;

public class Show {
    Movie movie;
    LocalTime startTime;
    CinemaHall hall;

    public Show(Movie movie, LocalTime startTime, CinemaHall hall) {
        this.movie = movie;
        this.startTime = startTime;
        this.hall = hall;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public CinemaHall getHall() {
        return hall;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
        return false;
        }
        Show show = (Show) obj;
        return this.movie.equals(show.movie) && this.startTime.equals(show.startTime) && this.hall.equals(show.hall);
    }

    @Override
    public int hashCode() {
        return movie.hashCode() + startTime.hashCode() + hall.hashCode();
    }
}
