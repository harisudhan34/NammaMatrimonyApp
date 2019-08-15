package com.skyappz.namma.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {States.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StatesDao taskDao();
}