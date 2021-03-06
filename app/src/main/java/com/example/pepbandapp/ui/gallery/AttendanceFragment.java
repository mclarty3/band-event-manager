package com.example.pepbandapp.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pepbandapp.Event;
import com.example.pepbandapp.MainActivity;
import com.example.pepbandapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AttendanceFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private Intent intent;
    private MainActivity activity;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
            {
                parent.removeView(view);
            }
        }
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        try {
            view = inflater.inflate(R.layout.fragment_attendance, container, false);
        }
        catch (InflateException e)
        {
            Log.d("BAD STUFF", " ");
        }
//        final TextView textView = root.findViewById(R.id.attendance_recorded_textview);
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        /*intent = new Intent(getActivity(), com.example.pepbandapp.AttendanceFragment.class);
        activity = (MainActivity) getActivity();
        if (activity != null) {
            intent.putExtra("currentlyDisplayedEvent", activity.eventList.get(0));
            //intent.putExtra("count", activity.eventList.size());
            intent.putParcelableArrayListExtra("eventList", activity.eventList);
            intent.putParcelableArrayListExtra("memberList", activity.memberList);
        }
        getActivity().startActivity(intent);*/

        return view;
    }
}
