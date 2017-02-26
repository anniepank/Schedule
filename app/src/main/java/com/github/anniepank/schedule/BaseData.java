package com.github.anniepank.schedule;

import java.util.UUID;

/**
 * Created by anya on 2/26/17.
 */

public class BaseData {
    public String id;
    public BaseData() {
        this.id = UUID.randomUUID().toString();
    }
}
