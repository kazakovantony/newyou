package com.kazakov.newyou.app.model.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sensors_records_batch")
public class SensorsRecordsBatch {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = "athlete_name")
    private String athleteName;

    @DatabaseField(columnName = "sensors_records")
    private String sensorsRecords;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }

    public String getSensorsRecords() {
        return sensorsRecords;
    }

    public void setSensorsRecords(String sensorsRecords) {
        this.sensorsRecords = sensorsRecords;
    }
}
