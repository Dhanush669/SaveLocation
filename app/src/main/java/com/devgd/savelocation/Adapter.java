package com.devgd.savelocation;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.Notes>{
    private List<LocationEntity> locHistory =new ArrayList<>();
    Context context;
    int col;
    String des;
    public Adapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public Notes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layour,parent,false);
        return new Notes(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final Notes holder, final int position) {
        LocationEntity entity=locHistory.get(position);
        holder.longitude.setText(entity.getLongitude());
        holder.latitude.setText(entity.getLatitude());
        holder.time.setText(entity.getTime());

        col= new Random().nextInt(4);
        switch (col){
            case 0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.cardView.setCardBackgroundColor(context.getColor(R.color.color1));
                }
                break;
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.cardView.setCardBackgroundColor(context.getColor(R.color.color2));
                }
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.cardView.setCardBackgroundColor(context.getColor(R.color.color3));
                }
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.cardView.setCardBackgroundColor(context.getColor(R.color.colordef));
                }
        }
    }
    @Override
    public int getItemCount() {
        return locHistory.size();
    }

    public void setLocHistory(List<LocationEntity> locHistory){
        this.locHistory=locHistory;
    }


    public static class Notes extends RecyclerView.ViewHolder{
        TextView longitude,latitude,time;
        CardView cardView;
        public Notes(@NonNull View itemView) {
            super(itemView);
            longitude=itemView.findViewById(R.id.longitude);
            latitude=itemView.findViewById(R.id.latitude);
            time=itemView.findViewById(R.id.check_in_time);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }

}
