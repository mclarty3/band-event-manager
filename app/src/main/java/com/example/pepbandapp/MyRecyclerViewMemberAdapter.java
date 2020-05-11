package com.example.pepbandapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewMemberAdapter extends RecyclerView.Adapter<MyRecyclerViewMemberAdapter.ViewHolder> {

    public List<Member> memberList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MyRecyclerViewMemberAdapter(Context context, List<Member> members) {
        memberList = members;
        mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public MyRecyclerViewMemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_member_row, parent, false);
        return new MyRecyclerViewMemberAdapter.ViewHolder(view);
    }

/*@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
}*/

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = memberList.get(position).get_name();
        String year = memberList.get(position).get_year();
        String instrument = memberList.get(position).get_instrument();
        String email = memberList.get(position).get_email();

        holder.name.setText(name);
        holder.year.setText(year);
        holder.instrument.setText(instrument);
        holder.email.setText(email);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return memberList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView year;
        TextView instrument;
        TextView email;


        ViewHolder(View itemView) {
            super(itemView);
            final ViewHolder thisHolder = this;
            name = itemView.findViewById(R.id.member_name);
            year = itemView.findViewById(R.id.member_year);
            instrument = itemView.findViewById(R.id.member_instrument);
            email = itemView.findViewById(R.id.member_email);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}

