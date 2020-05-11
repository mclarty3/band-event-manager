package com.example.pepbandapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class EventsFragment extends MainActivity {

    MyRecyclerViewEventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.fragment_schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        // Set up event list
        final RecyclerView recyclerView = findViewById(R.id.event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewEventAdapter(context, eventList);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        drawer = findViewById(R.id.schedule_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        GetIntentExtras(i);

        if (navigationView.getMenu().findItem(selectedMenuID) != null)
        {
            navigationView.getMenu().findItem(selectedMenuID).setChecked(true);
        }
    }
}
