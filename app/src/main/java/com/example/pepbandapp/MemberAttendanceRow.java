package com.example.pepbandapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberAttendanceRow implements Parcelable {
    public Member member;
    public boolean attended;
    public boolean emailed;

    public MemberAttendanceRow(Member mem, boolean attend, boolean email)
    {
        member = mem;
        attended = attend;
        emailed = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        member.writeToParcel(dest, flags);
        dest.writeInt(attended ? 1 : 0);
        dest.writeInt(emailed ? 1 : 0);
    }

    public static final Parcelable.Creator<MemberAttendanceRow> CREATOR = new Parcelable.Creator<MemberAttendanceRow>() {
        public MemberAttendanceRow createFromParcel(Parcel in) {
            return new MemberAttendanceRow(in);
        }

        public MemberAttendanceRow[] newArray(int size) {
            return new MemberAttendanceRow[size];
        }
    };

    private MemberAttendanceRow(Parcel in)
    {
        member = Member.CREATOR.createFromParcel(in);
        attended = in.readInt() == 1;
        emailed = in.readInt() == 1;
    }
}
