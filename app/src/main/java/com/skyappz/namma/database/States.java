package com.skyappz.namma.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class States implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "state")
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", state = " + state + "]";
    }
}