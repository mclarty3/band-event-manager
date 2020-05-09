package com.example.pepbandapp.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pepbandapp.Event;
import com.example.pepbandapp.MainActivity;
import com.example.pepbandapp.R;

import java.util.List;

public class AttendanceFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);
//        final TextView textView = root.findViewById(R.id.attendance_recorded_textview);
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Intent intent = new Intent(getActivity(), com.example.pepbandapp.AttendanceFragment.class);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            intent.putExtra("currentlyDisplayedEvent", activity.eventList.get(0));
            //intent.putExtra("count", activity.eventList.size());
            intent.putParcelableArrayListExtra("eventList", activity.eventList);
            intent.putParcelableArrayListExtra("memberList", activity.memberList);
        }
        getActivity().startActivity(intent);

        return root;
    }
}
