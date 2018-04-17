package ru.ssermakov.newrecycler.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cases",
        foreignKeys = {
                @ForeignKey(entity = Patient.class,
                        parentColumns = "id",
                        childColumns = "patientId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Illness.class,
                        parentColumns = "id",
                        childColumns = "illnessId",
                        onDelete = ForeignKey.CASCADE)})
public class Case {

    @PrimaryKey(autoGenerate = true)
    Long id;
    Long patientId;
    Long illnessId;
    Date startDate;
    Date endDate;

    public Case() {
    }

    public Case(Long patientId, Long illnessId, Date startDate, Date endDate) {
        this.patientId = patientId;
        this.illnessId = illnessId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getIllnessId() {
        return illnessId;
    }

    public void setIllnessId(Long illnessId) {
        this.illnessId = illnessId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
