package com.example.pepbandapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String query = "CREATE TABLE " + TABLE_MEMBERS + "(" + COLUMN_NAME + " TEXT, " + COLUMN_YEAR + " TEXT, " + COLUMN_INSTRUMENT + " TEXT, " + COLUMN_EMAIL + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
                // dbString += recordSet.getString(recordSet.getColumnIndex("dish"));
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
                // dbString += recordSet.getString(recordSet.getColumnIndex("dish"));
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

}
