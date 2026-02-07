package com.example.eecs4443lab.data.list;

public abstract class ListItem {
    private final int id;
    private final String title;
    private final int imageResId;
    private final String description;
    public ListItem(int id, String title, int imageResId, String description) {
        this.id = id;
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
    }


    public int getId() {return id;}
    public int getImageResId() {return imageResId;}
    public String getTitle() {return title;}

    @Override
    public String toString() {
        return description;
    }


}

