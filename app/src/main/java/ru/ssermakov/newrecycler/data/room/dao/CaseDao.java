package ru.ssermakov.newrecycler.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Illness;

@Dao
public interface CaseDao {

    @Query("SELECT * FROM cases")
    List<Illness> getAll();

    @Query("SELECT * FROM cases WHERE id = :id")
    Illness getById(long id);

    @Insert
    Long insert(Case fact);

    @Update
    void update(Case fact);

    @Delete
    void delete(Case fact);

}
