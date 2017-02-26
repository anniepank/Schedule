package com.github.anniepank.schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.LinkedList;

/**
 * Created by anya on 2/5/17.
 */

public class AppDataLoader {
    public static final String NAME = "AppData";
    public static final String KEY = "data";

    public static AppData load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, 0);
        String json = prefs.getString(KEY, null);
        if (json != null) Log.d("APPDATA LOADER", json);
        if (json == null) {
            AppData appData = new AppData();
            appData.tasks = new LinkedList<>();
            appData.tasks.add(new TaskData("Task1", "Desc 1"));
            appData.tasks.add(new TaskData("Task2", "Desc 2"));
            appData.tasks.add(new TaskData("Task3", "Desc 3"));
            return appData;
        }

        return new Gson().fromJson(json, AppData.class);
    }

    public static void save(Context context, AppData appData) {
        String json = new Gson().toJson(appData);
        SharedPreferences prefs = context.getSharedPreferences(NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY, json).apply();
        Log.d("Settings", json);
    }
}
