package entity;

public class Movie {
    String name;
    int length;

    public Movie(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Movie movie = (Movie) obj;
        return this.name.equals(movie.name) && this.length == movie.length;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + length;
    }

}
