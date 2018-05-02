package ru.ssermakov.newrecycler.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

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
    private String imageUrl;

    public Patient() {
    }

    public Patient(String name, Date dateOfBirth, String state, String imageUrl) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.state = state;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
