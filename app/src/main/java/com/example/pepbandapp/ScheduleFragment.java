package com.example.pepbandapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
