package com.example.pepbandapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventsFragment extends MainActivity {

    TextView nameTextView, infoTextView, locationTextView, dateTextView, scheduleTextView;
    EditText eventNameEditText, eventInfoEditText, eventLocationEditText, eventDateEditText;
    MyRecyclerViewEventAdapter adapter;
    EventsHandler dbHandler;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        nameTextView = findViewById(R.id.name_textview);
        infoTextView = findViewById(R.id.info_textview);
        locationTextView = findViewById(R.id.textView_Location);
        dateTextView = findViewById(R.id.textView_date);
        eventNameEditText = findViewById(R.id.eventname_edittext);
        eventInfoEditText = findViewById(R.id.eventinfo_edittext);
        eventLocationEditText = findViewById(R.id.eventlocation_edittext);
        eventDateEditText = findViewById(R.id.date_edittext);



        drawer = findViewById(R.id.schedule_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        GetIntentExtras(i);

        if (navigationView.getMenu().findItem(selectedMenuID) != null)
        {
            navigationView.getMenu().findItem(selectedMenuID).setChecked(true);
        }

        // Set up event list
        recyclerView = findViewById(R.id.event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewEventAdapter(context, eventList);
        recyclerView.setAdapter(adapter);

        dbHandler = MainActivity.eventsDB;
    }

    @Override
    public void RefreshActivityDisplay()
    {
        adapter = new MyRecyclerViewEventAdapter(getApplicationContext(), eventList);
        recyclerView.setAdapter(adapter);
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
    }

    //Add an event to the database
    public void addButtonClicked(View view){
        if (eventDateEditText.getText().toString().length() == 10 &&
            eventDateEditText.getText().toString().charAt(2) == '/' &&
            eventDateEditText.getText().toString().charAt(5) == '/')
        {
            String name = eventNameEditText.getText().toString();
            String info = eventInfoEditText.getText().toString();
            String location = eventLocationEditText.getText().toString();
            String dateString = eventDateEditText.getText().toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (java.text.ParseException e)
            {
                e.printStackTrace();
            }
            Event event = new Event(name, info, location, date, memberList);
            if (event.get_date() == null)
            {
                Log.d("test", "DATE IS NULL");
            }
            dbHandler.addEvent(event);
            eventList.add(event);
            adapter = new MyRecyclerViewEventAdapter(this, eventList);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            Log.d("AddEvent", "Your date has to be formatted mm/dd/yyyy!");
            return;
        }
        ArrayList<Member> test = new ArrayList<>();
        test.add(new Member("Test", "tetst", "temst", "Tset"));
        Event event =
                new Event(eventNameEditText.getText().toString(), eventInfoEditText.getText().toString(), eventLocationEditText.getText().toString(), new Date(), test);
        dbHandler.addEvent(event);
        printDatabase();
    }

    //Delete event
    public void deleteButtonClicked(View view){
        String inputText = eventNameEditText.getText().toString();
        dbHandler.deleteEvent(inputText);
        printDatabase();
    }

    public void readInEvents(View view) {
        InputStream is = getResources().openRawResource(R.raw.events_data);
        eventList = dbHandler.readInData(is);
        for (Event event: eventList)
        {
            for (Member member: memberList) {
                event.AddMemberAttendance(member);
            }
        }
        dbHandler.close();
        adapter = new MyRecyclerViewEventAdapter(getApplicationContext(), eventList);
        recyclerView.setAdapter(adapter);
        currentlyDisplayedEvent = eventList.get(0);
    }
}
