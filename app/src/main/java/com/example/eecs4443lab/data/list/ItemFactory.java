package com.example.eecs4443lab.data.list;
import java.util.Random;
public class ItemFactory {
    private int currentId = 0;
    private final Random random = new Random();

    private int nextId() {
        return ++currentId;
    }

    public Book getBook() {


        return new Book(
                nextId(),
                "Dummy Book" + currentId,
                101,
                "This is an dummy book " + random.nextInt(1000),
                "unknown author"
        );
    }

    public Movie getMovie() {
        return new Movie(
                nextId(),
                "Dummy Movie " + currentId,
                202,
                "description of movie",
                "director"
        );
    }
}
