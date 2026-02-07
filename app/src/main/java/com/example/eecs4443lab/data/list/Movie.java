package com.example.eecs4443lab.data.list;

public class Movie extends ListItem {
    private final String director;

    public Movie(int id, String name, int imageResId, String description, String director) {
        super(id, name, imageResId, description);
        this.director = director;
    }

    public String getDirector() { return director; }
}