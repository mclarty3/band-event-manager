package com.example.pepbandapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleFragment extends AppCompatActivity{

    TextView nameTextView, infoTextView, locationTextView, dateTextView, scheduleTextView;
    EventsHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        nameTextView = (TextView) findViewById(R.id.name_textview);
        infoTextView = (TextView) findViewById(R.id.info_textview);
        locationTextView = (TextView) findViewById(R.id.textView_Location);
        dateTextView = (TextView) findViewById(R.id.textView_date);
        scheduleTextView = (TextView) findViewById(R.id.schedule_textview);
        /* Can pass nulls because of the constants in the helper.
         * the 1 means version 1 so don't run update.
         */
        dbHandler = new EventsHandler(this);
        printDatabase();
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        scheduleTextView.setText(dbString);
    }
}
