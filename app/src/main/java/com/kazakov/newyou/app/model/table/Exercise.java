package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;
import com.kazakov.newyou.app.model.GymActivity;

public class Exercise {

    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField(columnName = "type")
    protected GymActivity type;

    @DatabaseField(columnName = "iteration_amount")
    protected int iterationAmount;

    @DatabaseField(canBeNull = false, foreign = true)
    private SensorsRecordsBatch sensorsRecordsBatch;

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
}
