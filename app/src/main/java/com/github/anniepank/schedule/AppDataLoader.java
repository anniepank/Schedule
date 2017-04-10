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
            appData.classes = new LinkedList<>();
            appData.subjects = new LinkedList<>();

            appData.classes.add(new ClassData());
            appData.classes.add(new ClassData());
            appData.classes.add(new ClassData());
            appData.classes.add(new ClassData());

            appData.subjects.add(new SubjectData("Progr"));

            appData.classes.get(0).day = 0;
            appData.classes.get(0).timeSlot = 0;
            appData.classes.get(0).subjectId = appData.subjects.get(0).id;

            appData.classes.get(1).day = 0;
            appData.classes.get(1).timeSlot = 1;
            appData.classes.get(1).subjectId = appData.subjects.get(0).id;

            appData.classes.get(2).day = 1;
            appData.classes.get(2).timeSlot = 2;
            appData.classes.get(2).subjectId = appData.subjects.get(0).id;

            appData.classes.get(3).day = 3;
            appData.classes.get(3).timeSlot = 1;
            appData.classes.get(3).subjectId = appData.subjects.get(0).id;

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
