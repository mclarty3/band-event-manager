package com.example.pepbandapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends MainActivity implements MyRecyclerViewAttendanceAdapter.ItemClickListener{
    TextView attendanceNameTextView, attendanceDateTextView;
    ImageButton previousEventButton, nextEventButton;
    EditText eventNameEditText, eventDateEditText;
    EventsHandler dbHandler;
    Intent i;
    MyRecyclerViewAttendanceAdapter adapter;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_attendance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        attendanceNameTextView = findViewById(R.id.att_name_textview);
        attendanceDateTextView = findViewById(R.id.att_date_textview);
        previousEventButton = findViewById(R.id.back_button);
        nextEventButton = findViewById(R.id.forward_button);
        eventNameEditText = findViewById(R.id.event_name_edittext);
        eventDateEditText = findViewById(R.id.event_date_edittext);
        dbHandler = new EventsHandler(this);

        i = getIntent();
        GetIntentExtras(i);

        if (currentlyDisplayedEvent == null)
        {
            SetEventStrings(null, null);
        }
        else {
            SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
        }

        for (int event = 0; event < eventList.size(); event++)
        {
            if (eventList.get(event) == currentlyDisplayedEvent)
            {
                eventList.get(event).UpdateEvent(currentlyDisplayedEvent);
                break;
            }
        }

        // Set up attendance list
        recyclerView = findViewById(R.id.attendance_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAttendanceAdapter(context, memberList, currentlyDisplayedEvent);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        drawer = findViewById(R.id.included_toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (navigationView.getMenu().findItem(selectedMenuID) != null)
        {
            navigationView.getMenu().findItem(selectedMenuID).setChecked(true);
        }

        previousEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (previousEvent()) {
                    SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
                    recyclerView.setAdapter(new MyRecyclerViewAttendanceAdapter(context, memberList, currentlyDisplayedEvent));
                }
            }
        });

        nextEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (nextEvent()) {
                    SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
                    recyclerView.setAdapter(new MyRecyclerViewAttendanceAdapter(context, memberList, currentlyDisplayedEvent));
                }
            }
        });

        adapter.setClickListener(this);
    }

    @Override
    public void RefreshActivityDisplay()
    {
        if (currentlyDisplayedEvent == null)
        {
            SetEventStrings(null, null);
        }
        else {
            SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
        }
        adapter = new MyRecyclerViewAttendanceAdapter(context, memberList, currentlyDisplayedEvent);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void searchButtonClicked(View view) {
        String searchName = eventNameEditText.getText().toString();
        String searchDate = eventDateEditText.getText().toString();

        String answer = dbHandler.search(searchName, searchDate);
        String[] divided = answer.split(" ");

        attendanceNameTextView.setText(divided[0]);
        attendanceDateTextView.setText(divided[1]);
    }

    public void SetEventStrings(String name, Date date)
    {
        if (name == null || date == null)
        {
            attendanceNameTextView.setText("No events");
            attendanceDateTextView.setText("");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY", Locale.US);
        String eventDateString = simpleDateFormat.format(date);
        attendanceDateTextView.setText(eventDateString);
        attendanceNameTextView.setText(name);

    }

    public Boolean previousEvent()
    {
        if (eventList.size() == 0)
        {
            return false;
        }
        if (eventList.get(0).get_name().equals(currentlyDisplayedEvent.get_name()))
        {
            return false;
        }
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).get_name().equals(currentlyDisplayedEvent.get_name()))
            {
                eventList.get(i).UpdateEvent(currentlyDisplayedEvent);
                currentlyDisplayedEvent = eventList.get(i - 1);
                break;
            }
        }
        return true;
    }

    public Boolean nextEvent()
    {
        if (eventList.size() == 0)
        {
            return false;
        }
        if (eventList.get(eventList.size() - 1).get_name().equals(currentlyDisplayedEvent.get_name()))
        {
            return false;
        }
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).get_name().equals(currentlyDisplayedEvent.get_name()))
            {
                eventList.get(i).UpdateEvent(currentlyDisplayedEvent);
                currentlyDisplayedEvent = eventList.get(i + 1);
                break;
            }
        }
        return true;
    }

    @Override
    public void onItemClick() {
        i.putParcelableArrayListExtra("savedEventList", eventList);
        Log.d("Tag", "Pushing attendance");
    }
}
