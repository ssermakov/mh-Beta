package ru.ssermakov.newrecycler.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import ru.ssermakov.newrecycler.data.room.entity.Case;
import ru.ssermakov.newrecycler.data.room.entity.Illness;
import ru.ssermakov.newrecycler.data.room.entity.Patient;

@Dao
public interface CaseDao {

    @Query("SELECT * FROM cases")
    List<Case> getAll();

    @Query("SELECT * FROM cases WHERE id = :id")
    Case getById(long id);

    @Insert
    Long insert(Case fact);

    @Update
    void update(Case fact);

    @Delete
    void delete(Case fact);

    @Query("SELECT id FROM cases WHERE patientId = :patientId AND endDate IS NULL")
    List<Integer> selectOpenCaseId(Integer patientId);

    @Query("SELECT startDate FROM cases WHERE patientId = :patientId AND endDate IS NULL")
    Long getTimestampByPatientId(Integer patientId);

    @Query("select count(*) " +
            "from plans " +
            "inner join cases on caseId = cases.id " +
            "inner join patients on cases.patientId = patients.id " +
            "inner join illnesses on cases.illnessId = illnesses.id " +
            "where patients.id = :patientId and cases.endDate is null and `plan` is not null;")
    Integer isSchema(Integer patientId);

//    @Query("select * " +
//            "from cases " +
//            "inner join patients on cases.patientId = patients.id " +
//            "inner join illnesses on cases.illnessId = illnesses.id " +
//            "where patients.id = :patientId  order by cases.id desc;")
//    List<Case> getAllByPatientId(Integer patientId);
//
    @Query("select * " +
            "from cases " +
            "where patientId = :patientId  order by cases.id desc;")
    List<Case> getAllByPatientId(Integer patientId);
}
