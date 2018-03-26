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

    @PrimaryKey
    private int id;

    private String name;

    private Date dateOfBirth;

    private String state;

    private Uri image;

    public Patient(int id, String name, Date dateOfBirth, String state, Uri image) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getState() {
        return state;
    }

    public Uri getImage() {
        return image;
    }
}
