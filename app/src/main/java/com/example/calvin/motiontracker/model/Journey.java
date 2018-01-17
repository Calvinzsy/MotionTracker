package com.example.calvin.motiontracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "journey")
public class Journey {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "start_time")
    @TypeConverters(DateConverter.class)
    private Date startTime;

    @ColumnInfo(name = "end_time")
    @TypeConverters(DateConverter.class)
    private Date endTime;

    @TypeConverters(PathConverter.class)
    private List<Location> path = new ArrayList<>();

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Location> getPath() {
        return path;
    }

    public void setPath(List<Location> path) {
        this.path = path;
    }

    public void addLocation(Location location) {
        this.path.add(location);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
