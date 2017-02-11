package com.github.anniepank.schedule;

import java.util.UUID;

/**
 * Created by anya on 2/5/17.
 */
public class TaskData {
    public String name;
    public String description;
    public String id;


    TaskData(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID().toString();
    }

}
