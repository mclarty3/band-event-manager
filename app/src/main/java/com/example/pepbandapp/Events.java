package com.example.pepbandapp;

//import java.sql.Date;

public class Events {
    private String _name;
    private String _info;
    private String _location;
    private String _date;

    public Events(String name, String info, String location, String date) {
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

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
