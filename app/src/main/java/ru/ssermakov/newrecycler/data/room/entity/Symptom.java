package ru.ssermakov.newrecycler.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "symptoms",
        foreignKeys = {
                @ForeignKey(entity = Case.class,
                        parentColumns = "id",
                        childColumns = "caseId",
                        onDelete = ForeignKey.CASCADE)})
public class Symptom {

    @PrimaryKey(autoGenerate = true)
    Long id;
    Long caseId;
    String symptom;

    public Symptom(Long caseId, String symptom) {
        this.caseId = caseId;
        this.symptom = symptom;
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
}
