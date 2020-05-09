package com.example.pepbandapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    TextView attendanceNameTextView, attendanceDateTextView;
    ImageButton previousEventButton, nextEventButton;
    EditText eventNameEditText, eventDateEditText;
    EventsHandler dbHandler;
    ArrayList<Event> eventList;
    Event currentlyDisplayedEvent;

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_attendance);
        attendanceNameTextView = (TextView) findViewById(R.id.att_name_textview);
        attendanceDateTextView = (TextView) findViewById(R.id.att_date_textview);
        previousEventButton = findViewById(R.id.back_button);
        nextEventButton = findViewById(R.id.forward_button);
        eventNameEditText = (EditText) findViewById(R.id.event_name_edittext);
        eventDateEditText = (EditText) findViewById(R.id.event_date_edittext);
        dbHandler = new EventsHandler(this);

        Intent i = getIntent();
        currentlyDisplayedEvent = i.getParcelableExtra("currentlyDisplayedEvent");
        eventList = i.getParcelableArrayListExtra("eventList");

        previousEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                previousEvent();
                SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
            }
        });

        nextEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                nextEvent();
                SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
            }
        });

        // Test for populating list data
        ArrayList<String> names = new ArrayList<>();
        names.add("Ryan McLarty");
        names.add("Chiara Giammatteo");

        ArrayList<Boolean> attended = new ArrayList<>();
        attended.add(false);
        attended.add(true);

        ArrayList<Boolean> emailed = new ArrayList<>();
        emailed.add(false);
        emailed.add(false);

        // Set up attendance list
        RecyclerView recyclerView = findViewById(R.id.attendance_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, names, attended, emailed);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        SetEventStrings(currentlyDisplayedEvent.get_name(), currentlyDisplayedEvent.get_date());
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

    public void previousEvent()
    {
        if (eventList.get(0).get_name().equals(currentlyDisplayedEvent.get_name()))
        {
            return;
        }
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).get_name().equals(currentlyDisplayedEvent.get_name()))
            {
                currentlyDisplayedEvent = eventList.get(i - 1);
                break;
            }
        }
    }

    public void nextEvent()
    {
        if (eventList.get(eventList.size() - 1).get_name().equals(currentlyDisplayedEvent.get_name()))
        {
            return;
        }
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).get_name().equals(currentlyDisplayedEvent.get_name()))
            {
                currentlyDisplayedEvent = eventList.get(i + 1);
                break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
