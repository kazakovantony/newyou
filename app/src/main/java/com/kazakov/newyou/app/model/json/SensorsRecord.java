package com.kazakov.newyou.app.model.json;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class SensorsRecord {

    @Expose
    public long time;
    @Expose
    public float x;
    @Expose
    public float y;
    @Expose
    public float z;
    @Expose
    public float magn_z;
    @Expose
    public float ax;
    @Expose
    public float ay;
    @Expose
    public float az;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorsRecord that = (SensorsRecord) o;
        return time == that.time &&
                Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0 &&
                Float.compare(that.z, z) == 0 &&
                Float.compare(that.magn_z, magn_z) == 0 &&
                Float.compare(that.ax, ax) == 0 &&
                Float.compare(that.ay, ay) == 0 &&
                Float.compare(that.az, az) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, x, y, z, magn_z, ax, ay, az);
    }
}
