package com.devgd.savelocation;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = LocationEntity.class,version = 1)
public abstract class LocationRoom extends RoomDatabase {
    LocationDao locationDao;
    static LocationRoom instance;

    public abstract LocationDao getLocationDao();

    public static synchronized LocationRoom getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,LocationRoom.class,"Location_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
