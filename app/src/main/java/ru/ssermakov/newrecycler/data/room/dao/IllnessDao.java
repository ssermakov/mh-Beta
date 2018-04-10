package ru.ssermakov.newrecycler.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Illness;

@Dao
public interface IllnessDao {


    @Query("SELECT * FROM illnesses")
    List<Illness> getAll();

    @Query("SELECT * FROM illnesses WHERE id = :id")
    Illness getById(long id);

    @Insert
    Long insert(Illness illness);

    @Update
    void update(Illness illness);

    @Delete
    void delete(Illness illness);

}
