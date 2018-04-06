package ru.ssermakov.newrecycler.data.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.sql.Date;

/**
 * Created by btb_wild on 26.03.2018.
 */
@Entity(tableName = "patients")
public class Patient {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;

    private String dateOfBirth;

    private String state;

    private String image;

    public Patient(String name, String dateOfBirth, String state, String image) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.state = state;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getState() {
        return state;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
