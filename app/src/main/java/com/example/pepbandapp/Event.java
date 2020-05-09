package com.example.pepbandapp;

//import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Event implements Parcelable {
    private String _name;
    private String _info;
    private String _location;
    private Date _date;
    private Map<Member, Map.Entry<Boolean, Boolean>> eventAttendence;

    public Event(String name, String info, String location, Date date, List<Member> memberList) {
        this._name = name;
        this._info = info;
        this._location = location;
        this._date = date;
        for (Member member: memberList)
        {
            eventAttendence.put(member, new AbstractMap.SimpleEntry<>(false, false));
        }
    }

    public Event(String name, String info, String location, Date date, Map<Member, Map.Entry<Boolean, Boolean>> attendance) {
        this._name = name;
        this._info = info;
        this._location = location;
        this._date = date;
        this.eventAttendence = attendance;
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

    public Map.Entry<Boolean, Boolean> GetMemberAttendedEmailed(Member member)
    {
        if (eventAttendence != null)
            return eventAttendence.get(member);
        else
            return null;
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

        if (eventAttendence != null) {
            dest.writeInt(eventAttendence.size());
            if (eventAttendence.size() > 0) {
                for (Map.Entry<Member, Map.Entry<Boolean, Boolean>> entry : eventAttendence.entrySet()) {
                    dest.writeString(entry.getKey().get_name());
                    dest.writeString(entry.getKey().get_year());
                    dest.writeString(entry.getKey().get_instrument());
                    dest.writeString(entry.getKey().get_email());
                    dest.writeInt(entry.getValue().getKey() ? 1 : 0);   // Parsels attended
                    dest.writeInt(entry.getValue().getValue() ? 1 : 0); // Parsels emailed
                }
            }
        }
        else
        {
            dest.writeInt(0);
        }
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

        int size = in.readInt();
        for (int i = 0; i < size; i++)
        {
            String name = in.readString();
            String year = in.readString();
            String instrument = in.readString();
            String email = in.readString();
            Member member = new Member(name, year, instrument, email);
            Boolean attended = in.readInt() == 1;
            Boolean emailed = in.readInt() == 1;
            eventAttendence.put(member, new AbstractMap.SimpleEntry<>(attended, emailed));
        }
    }
}
