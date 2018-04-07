package ru.ssermakov.newrecycler.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by btb_wild on 26.03.2018.
 */

@Dao
public interface PatientDao {

    @Query("SELECT * FROM patients")
    List<Patient> getAll();

    @Query("SELECT * FROM patients WHERE id = :id")
    Patient getById(long id);

    @Insert
    Long insert (Patient patient);

    @Update
    void update (Patient patient);

    @Delete
    void delete (Patient patient);

}
