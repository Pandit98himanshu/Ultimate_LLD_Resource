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
}
