package com.example.pepbandapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EventsHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventDB.db";
    public static final String TABLE_EVENTS = "Events";
    //public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_INFO = "Info";
    public static final String COLUMN_LOCATION = "Location";
    public static final String COLUMN_DATE = "Date";

    //pass database info to superclass
    public EventsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EVENTS + "(" + COLUMN_NAME + "TEXT," + COLUMN_INFO + "TEXT," + COLUMN_LOCATION + "TEXT," + COLUMN_DATE + "DATE );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    //add a new row to the database
    public void addEvent(Event event){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.get_name());
        values.put(COLUMN_INFO, event.get_info());
        values.put(COLUMN_LOCATION, event.get_location());
        values.put(COLUMN_DATE, event.get_date().toString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    //delete a member from the database
    public void deleteEvent(String eventName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENTS + "WHERE" + COLUMN_NAME + "=" + eventName + ";");
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                // dbString += recordSet.getString(recordSet.getColumnIndex("Dish"));
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
        //dbString += "\n";

        db.close();
        return dbString;
    }

    public String search(String name, String date) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT Name FROM " + TABLE_EVENTS + " WHERE Name = " + name + "AND Date = " + date + ";";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                // dbString += recordSet.getString(recordSet.getColumnIndex("dish"));
                dbString += recordSet.getString(0);
                dbString += " ";
                dbString += recordSet.getString(1);
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public ArrayList<Event> GetEventList(ArrayList<Member> memberList)
    {
        ArrayList<Event> events = new ArrayList<>();
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Name")) != null) {
                String name = recordSet.getString(0);
                String info = recordSet.getString(1);
                String location = recordSet.getString(2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(recordSet.getString(3));
                } catch (java.text.ParseException e)
                {
                    e.printStackTrace();
                }
                events.add(new Event(name, info, location, date, memberList));
            }
            recordSet.moveToNext();
        }

        return events;
    }
}
