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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventsFragment extends MainActivity {

    TextView nameTextView, infoTextView, locationTextView, dateTextView, scheduleTextView;
    EditText eventNameEditText, eventInfoEditText, eventLocationEditText, eventDateEditText;
    MyRecyclerViewEventAdapter adapter;
    EventsHandler dbHandler;

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

        // Set up event list
        final RecyclerView recyclerView = findViewById(R.id.event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewEventAdapter(context, eventList);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

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

        dbHandler = MainActivity.eventsDB;
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        //scheduleTextView.setText(dbString); event list??
    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        ArrayList<Member> test = new ArrayList<>();
        test.add(new Member("Test", "tetst", "temst", "Tset"));
        Event event =
                new Event(eventNameEditText.getText().toString(), eventInfoEditText.getText().toString(), eventLocationEditText.getText().toString(), new Date(), test);
        dbHandler.addEvent(event);
        printDatabase();
    }

    //Delete items
    public void deleteButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        String inputText = eventNameEditText.getText().toString();
        dbHandler.deleteEvent(inputText);
        printDatabase();
    }

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public void readInData(View view) {
        InputStream is = getResources().openRawResource(R.raw.events_data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        while (true) {
            try {
                //split by ","
                String[] tokens = line.split(",");

                //read the data
                Event event = null;
                event.set_name(tokens[0]);
                event.set_info(tokens[1]);
                event.set_location(tokens[2]);
                Date date = formatter.parse(tokens[3]);
                event.set_date(date);
                dbHandler.addEvent(event);

                Log.d("ScheduleActivity", "Just created: " + event);

                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException | ParseException e) {
                Log.wtf("ScheduleActivity", "Error reading data file on line " + line, e);
                e.printStackTrace();
            }

        }
    }
}
