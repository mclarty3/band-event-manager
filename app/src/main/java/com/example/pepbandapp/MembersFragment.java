package com.example.pepbandapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MembersFragment extends AppCompatActivity {
    TextView studentNameTextView, yearTextView, instrumentTextView, emailTextView, membersTextView;
    MembersHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_members);
        studentNameTextView = (TextView) findViewById(R.id.studentname_textview);
        yearTextView = (TextView) findViewById(R.id.year_textview);
        instrumentTextView = (TextView) findViewById(R.id.instrument_textview);
        emailTextView = (TextView) findViewById(R.id.email_textview);
        membersTextView = (TextView) findViewById(R.id.members_textview);
        /* Can pass nulls because of the constants in the helper.
         * the 1 means version 1 so don't run update.
         */
        dbHandler = new MembersHandler(this);
        printDatabase();
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        membersTextView.setText(dbString);
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
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        while (true) {
            try {
                //split by ","
                String[] tokens = line.split(",");

                //read the data
                Member member = null;
                member.set_name(tokens[0]);
                member.set_year(tokens[1]);
                member.set_instrument(tokens[2]);
                member.set_email(tokens[3]);
                dbHandler.addMember(member);

                Log.d("ScheduleActivity", "Just created: " + member);

                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                Log.wtf("ScheduleActivity", "Error reading data file on line " + line, e);
                e.printStackTrace();
            }
        }
    }
}
