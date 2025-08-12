package entity;

import java.time.LocalTime;

public class Show {
    Movie movie;
    LocalTime startTime;

    public Show(Movie movie, LocalTime startTime) {
        this.movie = movie;
        this.startTime = startTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalTime getStartTime() {
        return startTime;
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
        return this.movie.equals(show.movie) && this.startTime.equals(show.startTime);
    }

    @Override
    public int hashCode() {
        return movie.hashCode() + startTime.hashCode();
    }
}
