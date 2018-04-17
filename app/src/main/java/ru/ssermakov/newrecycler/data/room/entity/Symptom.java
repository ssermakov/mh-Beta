package ru.ssermakov.newrecycler.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "symptoms",
        foreignKeys = {
                @ForeignKey(entity = Case.class,
                        parentColumns = "id",
                        childColumns = "caseId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Patient.class,
                        parentColumns = "id",
                        childColumns = "patientId",
                        onDelete = ForeignKey.CASCADE)})
public class Symptom {

    @PrimaryKey(autoGenerate = true)
    Long id;
    Long caseId;
    String symptom;
    Long patientId;

    public Symptom(Long patientId, Long caseId, String symptom) {
        this.caseId = caseId;
        this.symptom = symptom;
        this.patientId = patientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
