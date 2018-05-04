package ru.ssermakov.newrecycler.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Plan;
import ru.ssermakov.newrecycler.data.room.entity.Symptom;

@Dao
public interface PlanDao {

    @Query("SELECT * FROM plans")
    List<Illness> getAll();

    @Query("SELECT * FROM plans WHERE id = :id")
    Illness getById(long id);

    @Insert
    Long insert(Plan plan);

    @Update
    void update(Plan plan);

    @Delete
    void delete(Plan plan);

    @Query("SELECT `plan` from plans WHERE caseId = :caseId")
    List<String> selectPlansByCaseId(Long caseId);
}
