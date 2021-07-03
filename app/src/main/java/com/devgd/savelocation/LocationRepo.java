package com.devgd.savelocation;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationRepo {
    LocationRoom locationRoom;
    LocationDao locationDao;
    LiveData<List<LocationEntity>> history;
    ExecutorService executorService;

    LocationRepo(Application application){
        locationRoom=LocationRoom.getInstance(application);
        locationDao=locationRoom.getLocationDao();
        history=locationDao.getHistory();
        executorService = Executors.newFixedThreadPool(1);
    }

    LiveData<List<LocationEntity>> getHistory(){
        return history;
    }

    public void Insert(LocationEntity entity){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                locationDao.insert(entity);
            }
        });
    }

}
