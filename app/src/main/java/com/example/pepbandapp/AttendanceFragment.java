package com.example.pepbandapp;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttendanceFragment extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    TextView attendanceNameTextView, attendanceDateTextView;
    EditText eventNameEditText, eventDateEditText;
    EventsHandler dbHandler;

    MyRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstance) {
        Log.d("tag", "test");
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_attendance);
        attendanceNameTextView = (TextView) findViewById(R.id.att_name_textview);
        attendanceDateTextView = (TextView) findViewById(R.id.att_date_textview);
        eventNameEditText = (EditText) findViewById(R.id.event_name_edittext);
        eventDateEditText = (EditText) findViewById(R.id.event_date_edittext);
        dbHandler = new EventsHandler(this);

        // Test for populating list data
        ArrayList<String> names = new ArrayList<>();
        names.add("Ryan McLarty");
        names.add("Chiara Giammattteo");

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
        Log.d("tag", "setting adapter " + adapter.nameData.size());
    }

    public void searchButtonClicked(View view) {
        String searchName = eventNameEditText.getText().toString();
        String searchDate = eventDateEditText.getText().toString();

        String answer = dbHandler.search(searchName, searchDate);
        String[] divided = answer.split(" ");

        attendanceNameTextView.setText(divided[0]);
        attendanceDateTextView.setText(divided[1]);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
