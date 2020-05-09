package com.example.pepbandapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditEvents extends AppCompatActivity {

    EditText eventNameEditText, eventInfoEditText, eventLocationEditText, eventDateEditText;
    TextView displayEventsTextView;
    EventsHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editevents);
        eventNameEditText = (EditText) findViewById(R.id.eventname_edittext);
        eventInfoEditText = (EditText) findViewById(R.id.eventinfo_edittext);
        eventLocationEditText = (EditText) findViewById(R.id.eventlocation_edittext);
        eventDateEditText = (EditText) findViewById(R.id.date_edittext);
        displayEventsTextView = (TextView) findViewById(R.id.display_events_textview);

        /* Can pass nulls because of the constants in the helper.
         * the 1 means version 1 so don't run update.
         */
        dbHandler = new EventsHandler(this);
        printDatabase();
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        displayEventsTextView.setText(dbString);
    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        Events event =
                new Events(eventNameEditText.getText().toString(), eventInfoEditText.getText().toString(), eventLocationEditText.getText().toString(), eventDateEditText.getText().toString());
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

}
