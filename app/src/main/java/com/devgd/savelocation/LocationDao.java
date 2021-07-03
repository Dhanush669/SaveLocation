package com.devgd.savelocation;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert
    void insert(LocationEntity locationEntity);

    @Query("SELECT * FROM LocationEntity")
    LiveData<List<LocationEntity>> getHistory();
}
