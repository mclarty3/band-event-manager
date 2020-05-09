package com.example.pepbandapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceFragment extends AppCompatActivity {
    TextView attendanceNameTextView, attendanceDateTextView;
    EditText eventNameEditText, eventDateEditText;
    EventsHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_attendance);
        attendanceNameTextView = (TextView) findViewById(R.id.att_name_textview);
        attendanceDateTextView = (TextView) findViewById(R.id.att_date_textview);
        eventNameEditText = (EditText) findViewById(R.id.event_name_edittext);
        eventDateEditText = (EditText) findViewById(R.id.event_date_edittext);
        dbHandler = new EventsHandler(this);
    }

    public void searchButtonClicked(View view) {
        String searchName = eventNameEditText.getText().toString();
        String searchDate = eventDateEditText.getText().toString();

        String answer = dbHandler.search(searchName, searchDate);
        String[] divided = answer.split(" ");

        attendanceNameTextView.setText(divided[0]);
        attendanceDateTextView.setText(divided[1]);
    }
}
