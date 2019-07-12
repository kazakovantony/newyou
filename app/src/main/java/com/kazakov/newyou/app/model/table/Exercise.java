package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;
import com.kazakov.newyou.app.model.GymActivity;
import com.kazakov.newyou.app.model.table.base.Entity;

public abstract class Exercise implements Entity {

    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField (foreign = true, columnName = "workout", canBeNull = false)
    protected Workout workout;

    @DatabaseField(columnName = "type")
    protected GymActivity type;

    @DatabaseField(columnName = "iteration_amount")
    protected int iterationAmount;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "sensors_records_batch")
    protected SensorsRecordsBatch sensorsRecordsBatch;

    @DatabaseField(columnName = "duration")
    protected String duration;

    public SensorsRecordsBatch getSensorsRecordsBatch() {
        return sensorsRecordsBatch;
    }

    public void setSensorsRecordsBatch(SensorsRecordsBatch sensorsRecordsBatch) {
        this.sensorsRecordsBatch = sensorsRecordsBatch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GymActivity getType() {
        return type;
    }

    public void setType(GymActivity type) {
        this.type = type;
    }

    public int getIterationAmount() {
        return iterationAmount;
    }

    public void setIterationAmount(int iterationAmount) {
        this.iterationAmount = iterationAmount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
