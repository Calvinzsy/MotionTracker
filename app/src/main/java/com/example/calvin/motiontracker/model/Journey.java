package com.example.calvin.motiontracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "journey")
public class Journey implements Parcelable {

    public Journey() {

    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeLong(DateConverter.fromDate(startTime));
        parcel.writeLong(DateConverter.fromDate(endTime));
        parcel.writeTypedList(path);
    }

    private Journey(Parcel parcel) {
        id = parcel.readLong();
        startTime = DateConverter.toDate(parcel.readLong());
        endTime = DateConverter.toDate(parcel.readLong());
        path = new ArrayList<>();
        parcel.readTypedList(path, Location.CREATOR);
    }

    public static final Parcelable.Creator<Journey> CREATOR = new Creator<Journey>() {
        @Override
        public Journey createFromParcel(Parcel parcel) {
            return new Journey(parcel);
        }

        @Override
        public Journey[] newArray(int size) {
            return new Journey[size];
        }
    };
}
