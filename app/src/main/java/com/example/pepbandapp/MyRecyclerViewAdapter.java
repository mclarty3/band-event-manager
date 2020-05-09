package com.example.pepbandapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public List<String> nameData;
    private List<Boolean> attendedData;
    private List<Boolean> emailedData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> names, List<Boolean> attended, List<Boolean> emailed) {
        this.mInflater = LayoutInflater.from(context);
        this.nameData = names;
        this.attendedData = attended;
        this.emailedData = emailed;
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
        String name = nameData.get(position);
        Boolean attended = attendedData.get(position);
        Boolean emailed = emailedData.get(position);
        holder.rowMemberName.setText(name);
        holder.attended = attended;
        holder.attendedCheckBox.setChecked(attended);
        holder.emailed = emailed;
        holder.emailedCheckBox.setChecked(emailed);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return nameData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rowMemberName;
        CheckBox attendedCheckBox;
        CheckBox emailedCheckBox;

        Boolean attended;
        Boolean emailed;

        ViewHolder(View itemView) {
            super(itemView);
            rowMemberName = itemView.findViewById(R.id.attendance_member_name);
            attendedCheckBox = itemView.findViewById(R.id.attendedCheckBox);
            emailedCheckBox = itemView.findViewById(R.id.emailedCheckBox);
            attended = attendedCheckBox.isChecked();
            emailed = emailedCheckBox.isChecked();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return nameData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
