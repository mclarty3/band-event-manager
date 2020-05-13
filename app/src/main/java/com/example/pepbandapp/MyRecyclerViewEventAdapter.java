package com.example.pepbandapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewEventAdapter extends RecyclerView.Adapter<MyRecyclerViewEventAdapter.ViewHolder> {

    public List<Event> eventList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MyRecyclerViewEventAdapter(Context context, List<Event> events) {
        eventList = events;
        mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public MyRecyclerViewEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_event_row, parent, false);
        return new MyRecyclerViewEventAdapter.ViewHolder(view);
    }

    /*@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }*/

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String eventName = eventList.get(position).get_name();
        String eventLocation = eventList.get(position).get_location();
        String eventInfo = eventList.get(position).get_info();
        Date eventDate = eventList.get(position).get_date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY", Locale.US);

        holder.name.setText(eventName);
        holder.location.setText(eventLocation);
        holder.info.setText(eventInfo);
        holder.date.setText(simpleDateFormat.format(eventDate));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return eventList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView info;
        TextView location;
        TextView date;


        ViewHolder(View itemView) {
            super(itemView);
            final ViewHolder thisHolder = this;
            name = itemView.findViewById(R.id.event_name);
            info = itemView.findViewById(R.id.event_info);
            location = itemView.findViewById(R.id.event_location);
            date = itemView.findViewById(R.id.event_date);
        }
    }
}
