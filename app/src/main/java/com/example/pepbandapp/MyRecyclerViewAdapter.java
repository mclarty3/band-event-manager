package com.example.pepbandapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public List<Member> memberData;
    public List<String> nameData;
    private List<Boolean> attendedData;
    private List<Boolean> emailedData;
    public Event eventData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<Member> members, Event event) {
        this.mInflater = LayoutInflater.from(context);
        memberData = members;
        eventData = event;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_attendance_row, parent, false);
        return new ViewHolder(view);
    }

    /*@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }*/

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    // total number of rows
    @Override
    public int getItemCount() {
        return memberData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Member member;
        TextView rowMemberName;
        CheckBox attendedCheckBox;
        CheckBox emailedCheckBox;

        Boolean attended;
        Boolean emailed;

        ViewHolder(View itemView) {
            super(itemView);
            final ViewHolder thisHolder = this;
            rowMemberName = itemView.findViewById(R.id.attendance_member_name);
            attendedCheckBox = itemView.findViewById(R.id.attendedCheckBox);
            emailedCheckBox = itemView.findViewById(R.id.emailedCheckBox);
            attended = attendedCheckBox.isChecked();
            emailed = emailedCheckBox.isChecked();
            itemView.setOnClickListener(this);

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

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void UpdateAttendedEmailed(ViewHolder listItem)
    {
        Log.d("tag", "Updating checkbox");
        listItem.attended = listItem.attendedCheckBox.isChecked();
        listItem.emailed = listItem.emailedCheckBox.isChecked();
        /*Map.Entry<Boolean, Boolean> entry = eventData.GetMemberAttendedEmailed(listItem.member);
        if (entry != null)
        {
            eventData.SetMemberAttendedEmailed(listItem.member, listItem.attended, listItem.emailed);
        }*/
        eventData.SetEventMemberAttendance(listItem.member, listItem.attended, listItem.emailed);
        if (mClickListener != null) mClickListener.onItemClick();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return nameData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick();
    }
}
