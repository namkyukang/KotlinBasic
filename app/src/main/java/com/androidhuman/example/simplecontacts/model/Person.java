package com.androidhuman.example.simplecontacts.model;

import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Person extends RealmObject {

    public static final String PRIMARY_KEY = "id";

    @PrimaryKey
    private long id;

    @Required
    private String name;

    @Nullable
    private String address;
    //Realm같은 경우 반드시 getter setter를 만들어 줘야만 한다.
    //plugin에서 소스를 넣어주기 떄문에 로직을 추가하거나 해선 안된다.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}
