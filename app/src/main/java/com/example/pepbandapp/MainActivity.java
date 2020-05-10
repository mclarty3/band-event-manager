package com.example.pepbandapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    public ArrayList<Event> eventList = new ArrayList<>();
    public ArrayList<Member> memberList = new ArrayList<>();

    DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_events, R.id.nav_attendance, R.id.nav_members)
                .setDrawerLayout(drawer)
                .build();*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // This is what adds the menu button
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        memberList.add(new Member("Ryan McLarty", "2021", "Trumpet", "testR"));
        memberList.add(new Member("Chiara Giammatteo", "2021", "Trumpet", "testC"));
        memberList.add(new Member("David Andreas", "2022", "Percussion", "testD"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));

        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));

        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));

        Calendar cal = Calendar.getInstance(); // This is now the only way to do M/D/Y in Java!
        cal.set(2020, 1, 1);
        eventList.add(new Event("Test", "Test", "Test", cal.getTime(), memberList));
        cal.set(2020, 0, 23);
        eventList.add(new Event("Test2", "Test2", "Test2", cal.getTime(), memberList));
        SortEventDates();

        Log.d("tag2", eventList.get(0).memberAttendance.get(0).member.get_name());
        Log.d("tag2", Integer.toString(eventList.get(0).memberAttendance.get(0).attended ? 1 : 0));

        //memberList.get(0).set_name(String.valueOf(eventList.get(0).eventAttendence.get(memberList.get(0))));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public Member GetMember(String name)
    {
        for (Member member: memberList)
        {
            if (member.get_name() == name)
            {
                return member;
            }
        }
        return null;
    }

    private void SortEventDates()
    {
        for (int i = 0; i < eventList.size(); i++)
        {
            int j = i;
            while (j > 0 && eventList.get(j-1).get_date().after(eventList.get(j).get_date()))
            {
                Event temp = eventList.get(j-1);
                eventList.set(j-1, eventList.get(j));
                eventList.set(j, temp);
                j--;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (menuItem.isChecked())
        {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

        Intent intent = null;

        if (id == R.id.nav_events)
        {
            //startActivity(new Intent(getApplicationContext(), ))
        } else if (id == R.id.nav_attendance)
        {
            intent = new Intent(getApplicationContext(), AttendanceFragment.class);
            intent.putExtra("currentlyDisplayedEvent", eventList.get(0));
            intent.putParcelableArrayListExtra("eventList", eventList);
            intent.putParcelableArrayListExtra("memberList", memberList);
        } else if (id == R.id.nav_members)
        {

        }
        startActivity(intent);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
