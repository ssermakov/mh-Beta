package ru.ssermakov.newrecycler.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Symptom;

@Dao
public interface SymptomDao {

    @Query("SELECT * FROM symptoms")
    List<Illness> getAll();

    @Query("SELECT * FROM symptoms WHERE id = :id")
    Illness getById(long id);

    @Insert
    Long insert(Symptom symptom);

    @Update
    void update(Symptom symptom);

    @Delete
    void delete(Symptom symptom);

    @Query("SELECT symptom from symptoms WHERE caseId = :caseId")
    List<String> selectSymptomsByCaseId(Long caseId);

    @Query("DELETE from symptoms WHERE caseId = :caseId")
    void deleteSymptomsByCaseId(Long caseId);


}
