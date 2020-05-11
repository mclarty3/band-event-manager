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
    EditText eventNameEditText, eventInfoEditText, eventLocationEditText, eventDateEditText;
    EventsHandler dbHandler;
    List<Member> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        nameTextView = (TextView) findViewById(R.id.name_textview);
        infoTextView = (TextView) findViewById(R.id.info_textview);
        locationTextView = (TextView) findViewById(R.id.textView_Location);
        dateTextView = (TextView) findViewById(R.id.textView_date);
        eventNameEditText = (EditText) findViewById(R.id.eventname_edittext);
        eventInfoEditText = (EditText) findViewById(R.id.eventinfo_edittext);
        eventLocationEditText = (EditText) findViewById(R.id.eventlocation_edittext);
        eventDateEditText = (EditText) findViewById(R.id.date_edittext);
        //scheduleTextView = (TextView) findViewById(R.id.schedule_textview);
        /* Can pass nulls because of the constants in the helper.
         * the 1 means version 1 so don't run update.
         */
        dbHandler = new EventsHandler(this);
        printDatabase();
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
