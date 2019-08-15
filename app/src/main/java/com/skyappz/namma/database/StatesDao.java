package com.skyappz.namma.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StatesDao {

    @Query("SELECT * FROM States")
    List<States> getAll();

    @Insert
    void insert(States task);

    @Delete
    void delete(States task);

    @Update
    void update(States task);

}
