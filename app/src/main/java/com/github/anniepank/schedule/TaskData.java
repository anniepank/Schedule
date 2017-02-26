package com.github.anniepank.schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anya on 2/5/17.
 */
public class TaskData extends BaseData {
    private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance();
    public String name;
    public String description;
    public long date;
    public String room;

    public TaskData(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getFormattedDate() {
        if(date == 0) {
            return "Not set";
        }
        return DATE_FORMAT.format(new Date(this.date));
    }

}
