package com.example.pepbandapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MembersHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "memberDB.db";
    public static final String TABLE_MEMBERS = "Members";
    //public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_YEAR = "Year";
    public static final String COLUMN_INSTRUMENT = "Instrument";
    public static final String COLUMN_EMAIL = "Email";

    //pass database info to superclass
    public MembersHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_MEMBERS +
                "(" + COLUMN_NAME + " TEXT, " + COLUMN_YEAR + " TEXT, " +
                COLUMN_INSTRUMENT + " TEXT, " + COLUMN_EMAIL + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        onCreate(db);
    }

    public void DropTable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        onCreate(db);
    }

    //add a new row to the database
    public void addMember(Member member){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, member.get_name());
        values.put(COLUMN_YEAR, member.get_year());
        values.put(COLUMN_INSTRUMENT, member.get_instrument());
        values.put(COLUMN_EMAIL, member.get_email());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MEMBERS, null, values);
        db.close();
    }

    //delete a member from the database
    public void deleteMember(String memberName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MEMBERS + " WHERE " + COLUMN_NAME + " ='" + memberName + "';");
        db.close();
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                dbString += recordSet.getString(0);
                dbString += ",   ";
                //dbString += recordSet.getString(recordSet.getColumnIndex("Price"));
                dbString += recordSet.getString(1);
                dbString += ",   ";
                dbString += recordSet.getString(2);
                dbString += ",   ";
                dbString += recordSet.getString(3);
                dbString += "\n";
            }
            recordSet.moveToNext();
        }

        db.close();
        return dbString;
    }

    public String groupByYear(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_MEMBERS + " GROUP BY Year;";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("Year"));
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Name"));
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Instrument"));
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Email"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public ArrayList<Member> readIn(InputStream is) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Member> tempList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] colums = line.split(",");
                if (colums.length != 4) {
                    Log.d("CSVParser", "columns length = " + colums.length + "column[0] = " + colums[0] + " column[1] = " + colums[1] + " colum[2] = "+ colums[2] + " colum[3] = " + colums[3] + " colum[4] = " + colums[4]);
                    continue;
                }
                String name = colums[0];
                String year = colums[1];
                String instrument = colums[2];
                String email = colums[3];
                tempList.add(new Member(name, year, instrument, email));
                ContentValues cv = new ContentValues(3);
                cv.put(COLUMN_NAME, colums[0]);
                cv.put(COLUMN_YEAR, colums[1]);
                cv.put(COLUMN_INSTRUMENT, colums[2]);
                cv.put(COLUMN_EMAIL, colums[3]);
                db.insert(TABLE_MEMBERS, null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.close();
        return tempList;
    }

    public String groupByInstrument(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT Instrument, Name, Year, Email FROM " + TABLE_MEMBERS + " GROUP BY Instrument;";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("Instrument"));
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Name"));
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Year"));
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Email"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public ArrayList<Member> GetMemberList()
    {
        ArrayList<Member> members = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                Member member = new Member("","","","");
                member.set_name(recordSet.getString(0));
                member.set_year(recordSet.getString(1));
                member.set_instrument(recordSet.getString(2));
                member.set_email(recordSet.getString(3));
                members.add(member);
            }
            recordSet.moveToNext();
        }
        db.close();
        return members;
    }
}
