package com.example.pepbandapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends MainActivity implements MyRecyclerViewAdapter.ItemClickListener{
    TextView attendanceNameTextView, attendanceDateTextView;
    ImageButton previousEventButton, nextEventButton;
    EditText eventNameEditText, eventDateEditText;
    EventsHandler dbHandler;
    //ArrayList<Event> eventList = new ArrayList<>();
    //ArrayList<Member> memberList = new ArrayList<>();
    Intent i;

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_attendance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        attendanceNameTextView = (TextView) findViewById(R.id.att_name_textview);
        attendanceDateTextView = (TextView) findViewById(R.id.att_date_textview);
        previousEventButton = findViewById(R.id.back_button);
        nextEventButton = findViewById(R.id.forward_button);
        eventNameEditText = (EditText) findViewById(R.id.event_name_edittext);
        eventDateEditText = (EditText) findViewById(R.id.event_date_edittext);
        dbHandler = new EventsHandler(this);

        i = getIntent();
        GetIntentExtras(i);

        SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
        for (int event = 0; event < eventList.size(); event++)
        {
            if (eventList.get(event) == currentlyDisplayedEvent)
            {
                eventList.get(event).UpdateEvent(currentlyDisplayedEvent);
                break;
            }
        }

        // Set up attendance list
        final RecyclerView recyclerView = findViewById(R.id.attendance_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(context, memberList, currentlyDisplayedEvent);
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
                    recyclerView.setAdapter(new MyRecyclerViewAdapter(context, memberList, currentlyDisplayedEvent));
                }
            }
        });

        nextEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (nextEvent()) {
                    SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
                    recyclerView.setAdapter(new MyRecyclerViewAdapter(context, memberList, currentlyDisplayedEvent));
                }
            }
        });

        adapter.setClickListener(this);
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY", Locale.US);
        String eventDateString = simpleDateFormat.format(date);
        attendanceDateTextView.setText(eventDateString);
        attendanceNameTextView.setText(name);

    }

    public Boolean previousEvent()
    {
        if (eventList.get(0).get_name().equals(currentlyDisplayedEvent.get_name()))
        {
            return false;
        }
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).get_name().equals(currentlyDisplayedEvent.get_name()))
            {
                Log.d("event change", "updating event");
                eventList.get(i).UpdateEvent(currentlyDisplayedEvent);
                currentlyDisplayedEvent = eventList.get(i - 1);
                break;
            }
        }
        return true;
    }

    public Boolean nextEvent()
    {
        if (eventList.get(eventList.size() - 1).get_name().equals(currentlyDisplayedEvent.get_name()))
        {
            return false;
        }
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).get_name().equals(currentlyDisplayedEvent.get_name()))
            {
                Log.d("event change", "updating event");
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
