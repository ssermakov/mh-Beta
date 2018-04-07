package ru.ssermakov.newrecycler.data.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import ru.ssermakov.newrecycler.data.room.converter.DateConverter;

/**
 * Created by btb_wild on 26.03.2018.
 */
@Entity(tableName = "patients")
public class Patient {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private Date dateOfBirth;
    private String state;
    private String image;

    public Patient(String name, Date dateOfBirth, String state, String image) {
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

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
