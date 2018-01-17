package com.example.calvin.motiontracker.model;

import android.arch.persistence.room.TypeConverter;

import com.example.calvin.motiontracker.model.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PathConverter {

    @TypeConverter
    public static List<Location> toPath(String pathStr) {

        Type type = new TypeToken<List<Location>>(){}.getType();
        return new Gson().fromJson(pathStr, type);
    }

    @TypeConverter
    public static String fromPath(List<Location> path) {

        Type type = new TypeToken<List<Location>>(){}.getType();
        return new Gson().toJson(path, type);
    }
}
