package com.github.anniepank.schedule;

import android.content.Context;

import java.util.LinkedList;

/**
 * Created by anya on 2/5/17.
 */

public class AppData {
    public LinkedList<TaskData> tasks;
    public LinkedList<SubjectData> subjects;
    public LinkedList<ClassData> classes;


    public void save(Context context) {
        AppDataLoader.save(context, this);
    }

    public <T extends BaseData> T getById(String id, Class<T> cls) {
        LinkedList<? extends BaseData> list;
        if(cls == TaskData.class) {
            list = tasks;
        } else if (cls == SubjectData.class) {
            list = subjects;
        } else if (cls == ClassData.class) {
            list = classes;
        } else {
            throw new RuntimeException("Wrong type!");
        }
        for (BaseData element : list) {
            if (element.id.equals(id)) {
                return cls.cast(element);
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
