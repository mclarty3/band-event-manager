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
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        drawer = findViewById(R.id.members_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        studentNameTextView = (TextView) findViewById(R.id.studentname_textview);
        yearTextView = (TextView) findViewById(R.id.year_textview);
        instrumentTextView = (TextView) findViewById(R.id.instrument_textview);
        emailTextView = (TextView) findViewById(R.id.email_textview);
        memberNameEditText = (EditText) findViewById(R.id.membername_edittext);
        memberYearEditText = (EditText) findViewById(R.id.memberyear_edittext);
        memberInstrumentEditText = (EditText) findViewById(R.id.memberinstrument_edittext);
        memberEmailEditText = (EditText) findViewById(R.id.memberemail_edittext);
        /* Can pass nulls because of the constants in the helper.
         * the 1 means version 1 so don't run update.
         */

        Intent i = getIntent();
        GetIntentExtras(i);

        if (navigationView.getMenu().findItem(selectedMenuID) != null)
        {
            navigationView.getMenu().findItem(selectedMenuID).setChecked(true);
        }

        //dbHandler = new MembersHandler(this);
        dbHandler = MainActivity.memberDB;
        //printDatabase();
    }

    //Print the database
    public void printDatabase(){
        //String dbString = dbHandler.databaseToString();
        //membersTextView.setText(memberList.get(0).get_name());
    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addMemberButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        Member member =
                new Member(memberNameEditText.getText().toString(), memberYearEditText.getText().toString(), memberInstrumentEditText.getText().toString(), memberEmailEditText.getText().toString());
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
        // dbHandler delete needs string to find in the db
        String inputText = memberNameEditText.getText().toString();
        dbHandler.deleteMember(inputText);
        printDatabase();
    }

    public void groupByYear(View view) {
        String resultsString = dbHandler.groupByYear();
        membersTextView.setText(resultsString);
    }

    public void groupByInstrument(View view) {
        String resultsString = dbHandler.groupByInstrument();
        membersTextView.setText(resultsString);
    }

//    public void readInMembers(View view) {
//        InputStream is = getResources().openRawResource(R.raw.members_data);
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(is, Charset.forName("UTF-8"))
//        );
//
//        String line = "";
//        while (true) {
//            try {
//                //split by ","
//                String[] tokens = line.split(",");
//
//                //read the data
//                Member member = new Member();
//                member.set_name(tokens[0]);
//                member.set_year(tokens[1]);
//                member.set_instrument(tokens[2]);
//                member.set_email(tokens[3]);
//                dbHandler.addMember(member);
//
//                Log.d("ScheduleActivity", "Just created: " + member);
//
//                if (!((line = reader.readLine()) != null)) break;
//            } catch (IOException e) {
//                Log.wtf("ScheduleActivity", "Error reading data file on line " + line, e);
//                e.printStackTrace();
//            }
//        }
//    }

    public void readInMembers(View view) {
        InputStream is = getResources().openRawResource(R.raw.members_data);
        memberList = dbHandler.readIn(is);
    }
}
