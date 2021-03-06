package com.example.pepbandapp;

//import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Event implements Parcelable {
    private String _name;
    private String _info;
    private String _location;
    private Date _date;
    public List<MemberAttendanceRow> memberAttendance;

    public Event(String name, String info, String location, Date date, List<Member> memberList) {
        this._name = name;
        this._info = info;
        this._location = location;
        this._date = date;
        memberAttendance = new ArrayList<>();
        for (Member member: memberList)
        {
            memberAttendance.add(new MemberAttendanceRow(member, false, false));
        }
    }

    public static Date StringToDate(String string)
    {
        if (string.length() == 10 && string.charAt(2) == '/' && string.charAt(5) == '/')
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = null;
            try {
                date = simpleDateFormat.parse(string);
            } catch (java.text.ParseException e)
            {
                e.printStackTrace();
            }
            return date;
        }
        else
        {
            return null;
        }
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


    public MemberAttendanceRow GetEventMemberAttendance(Member member)
    {
        for (MemberAttendanceRow attendance: memberAttendance)
        {
            if (attendance.member.get_name().equals(member.get_name()))
            {
                return attendance;
            }
        }
        return null;
    }

    public void SetEventMemberAttendance(Member member, boolean attended, boolean emailed)
    {
        if (member == null)
            return;
        for (MemberAttendanceRow attendance: memberAttendance)
        {
            if (attendance.member.get_email().equals(member.get_email()))
            {
                attendance.attended = attended;
                attendance.emailed = emailed;
                return;
            }
        }
    }

    public void UpdateEvent(Event other)
    {
        if (!_name.equals(other.get_name()))
        {
            _name = other.get_name();
        }
        if (!_info.equals(other.get_info()))
        {
            _info = other.get_info();
        }
        if (!_location.equals(other.get_location()))
        {
            _location = other.get_location();
        }
        if (!_date.equals(other.get_date()))
        {
            _date = other._date;
        }
        if (!memberAttendance.equals(other.memberAttendance))
        {
            memberAttendance = other.memberAttendance;
        }
    }

    public void AddMemberAttendance(Member member)
    {
        memberAttendance.add(new MemberAttendanceRow(member, false, false));
    }

    public void EraseMemberAttendance()
    {
        for (MemberAttendanceRow attendance: memberAttendance)
        {
            attendance.attended = false;
            attendance.emailed = false;
        }
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
        if (memberAttendance != null) {
            dest.writeInt(memberAttendance.size());
            for (int i = 0; i < memberAttendance.size(); i++)
                memberAttendance.get(i).writeToParcel(dest, flags);
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
        memberAttendance = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            memberAttendance.add(MemberAttendanceRow.CREATOR.createFromParcel(in));
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "_name='" + _name + '\'' +
                ", _info='" + _info + '\'' +
                ", _location='" + _location + '\'' +
                ", _date=" + _date +
                ", memberAttendance=" + memberAttendance +
                '}';
    }
}
