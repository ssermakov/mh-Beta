package ru.ssermakov.newrecycler.data;

import android.net.Uri;

/**
 * Created by btb_wild on 19.02.2018.
 */

public class PersonItem {
    private String name;
    private int backgroundColor;
    private Uri image;
    private int id;
    private String state;

    public PersonItem(int id, String name, int backgroundColor, Uri image, String state) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.image = image;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Uri getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
