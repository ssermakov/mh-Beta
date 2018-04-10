package ru.ssermakov.newrecycler.data.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "plans",
        foreignKeys = {
                @ForeignKey(entity = Plan.class,
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
}
