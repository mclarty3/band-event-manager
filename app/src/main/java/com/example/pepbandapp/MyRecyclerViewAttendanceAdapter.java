package com.example.pepbandapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAttendanceAdapter extends RecyclerView.Adapter<MyRecyclerViewAttendanceAdapter.ViewHolder> {

    public List<Member> memberData;
    public Event eventData;
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAttendanceAdapter(Context context, List<Member> members, Event event) {
        this.mInflater = LayoutInflater.from(context);
        if (event == null)
        {
            memberData = new ArrayList<>();
            eventData = null;
            return;
        }
        memberData = members;
        eventData = event;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_attendance_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (eventData != null && eventData.memberAttendance.size() > 0) {
            String name = memberData.get(position).get_name();
            Boolean attended, emailed;
            attended = eventData.memberAttendance.get(position).attended;
            emailed = eventData.memberAttendance.get(position).emailed;
            holder.rowMemberName.setText(name);
            holder.attended = attended;
            holder.attendedCheckBox.setChecked(attended);
            holder.emailed = emailed;
            holder.emailedCheckBox.setChecked(emailed);
            holder.member = memberData.get(position);
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        if (holder.emailedCheckBox != null) {
            holder.emailedCheckBox.setOnCheckedChangeListener(null);
        }
        if (holder.attendedCheckBox != null)
        {
            holder.attendedCheckBox.setOnCheckedChangeListener(null);
        }
        super.onViewRecycled(holder);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return memberData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        Member member;
        TextView rowMemberName;
        CheckBox attendedCheckBox;
        CheckBox emailedCheckBox;

        boolean attended;
        boolean emailed;

        ViewHolder(View itemView) {
            super(itemView);
            final ViewHolder thisHolder = this;
            rowMemberName = itemView.findViewById(R.id.attendance_member_name);
            attendedCheckBox = itemView.findViewById(R.id.attendedCheckBox);
            emailedCheckBox = itemView.findViewById(R.id.emailedCheckBox);

            attendedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    UpdateAttendedEmailed(thisHolder);
                }
            });

            emailedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    UpdateAttendedEmailed(thisHolder);
                }
            });
        }
    }

    public void UpdateAttendedEmailed(ViewHolder listItem)
    {
        listItem.attended = listItem.attendedCheckBox.isChecked();
        listItem.emailed = listItem.emailedCheckBox.isChecked();
        eventData.SetEventMemberAttendance(listItem.member, listItem.attended, listItem.emailed);
    }

    /*// allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick();
    }*/
}
