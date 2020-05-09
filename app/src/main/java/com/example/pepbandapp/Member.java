package com.example.pepbandapp;

public class Member {
    private String _name;
    private String _year;
    private String _instrument;
    private String _email;

    public Member(String name, String year, String instrument, String email) {
        this._name = name;
        this._year = year;
        this._instrument = instrument;
        this._email = email;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String _year) {
        this._year = _year;
    }

    public String get_instrument() {
        return _instrument;
    }

    public void set_instrument(String _instrument) {
        this._instrument = _instrument;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_email() {
        return _email;
    }
}
