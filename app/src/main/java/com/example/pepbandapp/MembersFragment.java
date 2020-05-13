package com.example.pepbandapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class MembersFragment extends MainActivity {
    TextView studentNameTextView, yearTextView, instrumentTextView, emailTextView, membersTextView;
    EditText memberNameEditText, memberYearEditText, memberInstrumentEditText, memberEmailEditText;
    MembersHandler dbHandler;
    MyRecyclerViewMemberAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_members);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        // Set up attendance list
        recyclerView = findViewById(R.id.member_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewMemberAdapter(context, memberList);
        recyclerView.setAdapter(adapter);

        drawer = findViewById(R.id.members_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        studentNameTextView = findViewById(R.id.studentname_textview);
        yearTextView = findViewById(R.id.year_textview);
        instrumentTextView = findViewById(R.id.instrument_textview);
        emailTextView = findViewById(R.id.email_textview);
        memberNameEditText = findViewById(R.id.membername_edittext);
        memberYearEditText = findViewById(R.id.memberyear_edittext);
        memberInstrumentEditText = findViewById(R.id.memberinstrument_edittext);
        memberEmailEditText = findViewById(R.id.memberemail_edittext);

        Intent i = getIntent();
        GetIntentExtras(i);

        if (navigationView.getMenu().findItem(selectedMenuID) != null)
        {
            navigationView.getMenu().findItem(selectedMenuID).setChecked(true);
        }

        dbHandler = MainActivity.memberDB;
    }

    @Override
    public void RefreshActivityDisplay()
    {
        adapter = new MyRecyclerViewMemberAdapter(getApplicationContext(), memberList);
        recyclerView.setAdapter(adapter);
    }

    //Add a member to the database
    public void addMemberButtonClicked(View view){
        Member member = new Member(memberNameEditText.getText().toString(),
                memberYearEditText.getText().toString(), memberInstrumentEditText.getText().toString(),
                memberEmailEditText.getText().toString());
        dbHandler.addMember(member);
        memberList.add(member);
        for (Event event: eventList)
        {
            event.AddMemberAttendance(member);
        }
        adapter = new MyRecyclerViewMemberAdapter(this, memberList);
        recyclerView.setAdapter(adapter);
    }

    //Delete items
    public void deleteMemberButtonClicked(View view){
        String inputText = memberNameEditText.getText().toString();
        dbHandler.deleteMember(inputText);
    }

    public void groupByYear(View view) {
        String resultsString = dbHandler.groupByYear();
        membersTextView.setText(resultsString);
    }

    public void groupByInstrument(View view) {
        String resultsString = dbHandler.groupByInstrument();
        membersTextView.setText(resultsString);
    }

    public void readInMembers(View view) {
        InputStream is = getResources().openRawResource(R.raw.members_data);
        memberList = dbHandler.readIn(is);
        dbHandler.close();
        adapter = new MyRecyclerViewMemberAdapter(getApplicationContext(), memberList);
        recyclerView.setAdapter(adapter);
        for (Event event: eventList)
        {
            for (Member member: memberList) {
                event.AddMemberAttendance(member);
            }
        }
        if (eventList.size() > 0)
            currentlyDisplayedEvent = eventList.get(0);
    }
}
