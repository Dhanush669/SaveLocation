package com.devgd.savelocation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    LocationRepo locationRepo;
    LiveData<List<LocationEntity>> locationHistory;
    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationRepo=new LocationRepo(application);
        locationHistory=locationRepo.getHistory();
    }

    LiveData<List<LocationEntity>> getLocationHistory(){
        return locationHistory;
    }

    public void insert(LocationEntity entity){
        locationRepo.Insert(entity);
    }
}
