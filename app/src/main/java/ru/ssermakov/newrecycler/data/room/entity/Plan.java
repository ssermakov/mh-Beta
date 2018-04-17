package ru.ssermakov.newrecycler.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "plans",
        foreignKeys = {
                @ForeignKey(entity = Case.class,
                        parentColumns = "id",
                        childColumns = "caseId",
                        onDelete = ForeignKey.CASCADE)})
public class Plan {

    @PrimaryKey(autoGenerate = true)
    Long id;
    Long caseId;
    String plan;

    public Plan(Long caseId, String plan) {
        this.caseId = caseId;
        this.plan = plan;
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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
