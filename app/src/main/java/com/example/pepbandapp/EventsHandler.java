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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EventsHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventDB.db";
    private static final String TABLE_EVENTS = "Events";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_INFO = "Info";
    private static final String COLUMN_LOCATION = "Location";
    private static final String COLUMN_DATE = "Date";

    //pass database info to superclass
    public EventsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EVENTS + "(" + COLUMN_NAME + " TEXT," + COLUMN_INFO + " TEXT," + COLUMN_LOCATION + " TEXT," + COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public void DropTable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
        db.close();
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
        db.close();
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
                dbString += recordSet.getString(0);
                dbString += ",   ";
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

    public ArrayList<Event> readInData(InputStream is) {
        SQLiteDatabase db = getWritableDatabase();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        ArrayList<Event> events = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                String[] colums = line.split(",");
                if (colums.length != 4) {
                    Log.d("CSVParser", "columns length = " + colums.length + "column[0] = " + colums[0] + " column[1] = " + colums[1] + " colum[2] = "+ colums[2] + " colum[3] = " + colums[3] + " colum[4] = " + colums[4]);
                    continue;
                }


                String Name = colums[0];
                String Info = colums[1];
                String Location = colums[2];
                String Date = colums[3];
                Log.d("test", Date);
                Event event = new Event(Name, Info, Location, Event.StringToDate(Date), new ArrayList<Member>());
                events.add(event);
                Log.d("test", (event.get_date() == null ? "null":"not null"));



                ContentValues cv = new ContentValues(3);
                cv.put(COLUMN_NAME, colums[0]);
                cv.put(COLUMN_INFO, colums[1]);
                cv.put(COLUMN_LOCATION, colums[2]);
                cv.put(COLUMN_DATE, colums[3]);
                db.insert(TABLE_EVENTS, null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.close();
        return events;
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
                    if (date == null)
                        Log.d("DB", "DATE IS NULL");
                } catch (java.text.ParseException e)
                {
                    e.printStackTrace();
                }
                events.add(new Event(name, info, location, date, memberList));
            }
            recordSet.moveToNext();
        }
        db.close();
        return events;
    }
}
