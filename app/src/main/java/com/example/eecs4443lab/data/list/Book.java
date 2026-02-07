package com.example.eecs4443lab.data.list;

public class Book extends ListItem {
    private final String author;
    public Book(int id, String title, int imageResId, String description, String author) {
        super(id, title, imageResId, description);
        this.author = author;
    }
}
