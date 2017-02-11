package com.github.anniepank.schedule;

import android.content.Context;

import java.util.LinkedList;

/**
 * Created by anya on 2/5/17.
 */

public class AppData {
    public LinkedList<TaskData> tasks;


    public void save(Context context) {
        AppDataLoader.save(context, this);
    }

    public TaskData getTaskById(String id) {

        for (TaskData element : tasks) {
            if (element.id.equals(id)) {
                return element;
            }
        }
        return null;
    }


    private static AppData instance;

    public static AppData get(Context context) {
        if (instance == null) {
            instance = AppDataLoader.load(context);
        }
        return instance;
    }

}
