package com.devgd.savelocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final int Permission_code_for_loc = 1;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fClient;
    LocationViewModel viewModel;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewModel=new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(LocationViewModel.class);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            reqPer();
        } else {
            granted();
        }
    }

    public void reqPer(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Permission_code_for_loc);
    }

    private void granted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            reqPer();
            return;
        }
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.displayMap);
        fClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = fClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            latLng=new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions marker=new MarkerOptions().position(latLng)
                                    .title("Your Current Location");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            googleMap.addMarker(marker);
                        }
                    });
                }
            }
        });
    }

    public void add(View view) {
        String time;
        time= Calendar.getInstance().getTime().getHours()+" : "+Calendar.getInstance().getTime().getMinutes();
        LocationEntity entity=new LocationEntity(String.valueOf(latLng.longitude),
                String.valueOf(latLng.latitude),time);
        viewModel.insert(entity);
        Toast.makeText(this, "Added to Check-in List", Toast.LENGTH_SHORT).show();
    }

    public void checkHistory(View view) {
        Intent intent=new Intent(getApplicationContext(),LocationHistory.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission_code_for_loc) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                granted();
            }
        }
    }
}