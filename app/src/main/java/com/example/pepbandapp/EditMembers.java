package com.example.pepbandapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditMembers extends AppCompatActivity {

    EditText memberNameEditText, memberYearEditText, memberInstrumentEditText, memberEmailEditText;
    TextView displayMembersTextView;
    MembersHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmembers);
        memberNameEditText = (EditText) findViewById(R.id.membername_edittext);
        memberYearEditText = (EditText) findViewById(R.id.memberyear_edittext);
        memberInstrumentEditText = (EditText) findViewById(R.id.memberinstrument_edittext);
        memberEmailEditText = (EditText) findViewById(R.id.memberemail_edittext);
        displayMembersTextView = (TextView) findViewById(R.id.display_members_textview);

        /* Can pass nulls because of the constants in the helper.
         * the 1 means version 1 so don't run update.
         */
        dbHandler = new MembersHandler(this);
        printDatabase();
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        displayMembersTextView.setText(dbString);
    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addMemberButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        Members member =
                new Members(memberNameEditText.getText().toString(), memberYearEditText.getText().toString(), memberInstrumentEditText.getText().toString(), memberEmailEditText.getText().toString());
        dbHandler.addMember(member);
        printDatabase();
    }

    //Delete items
    public void deleteMemberButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        String inputText = memberNameEditText.getText().toString();
        dbHandler.deleteMember(inputText);
        printDatabase();
    }


}
