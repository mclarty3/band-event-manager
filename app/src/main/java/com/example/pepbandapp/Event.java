package com.example.pepbandapp;

//import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Event implements Parcelable {
    private String _name;
    private String _info;
    private String _location;
    private Date _date;

    public Event(String name, String info, String location, Date date) {
        this._name = name;
        this._info = info;
        this._location = location;
        this._date = date;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_info() {
        return _info;
    }

    public void set_info(String _info) {
        this._info = _info;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_info);
        dest.writeString(_location);
        dest.writeLong(_date.getTime());
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in)
    {
        _name = in.readString();
        _info = in.readString();
        _location = in.readString();
        _date = new Date(in.readLong());
    }
}
