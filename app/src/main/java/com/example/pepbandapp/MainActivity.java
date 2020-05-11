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
    public Event currentlyDisplayedEvent;
    public int selectedMenuID;

    public static boolean firstStart = true;
    public static MembersHandler memberDB;
    public static EventsHandler eventsDB;

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

        memberDB = new MembersHandler(getApplicationContext());
        eventsDB = new EventsHandler(getApplicationContext());

        memberDB.DropTable();
        memberDB.addMember(new Member("test3", "Test", "test", "test"));

        if (memberList.size() == 0)
        {
            memberList = memberDB.GetMemberList();
            memberDB.close();
        }
        if (eventList.size() == 0)
        {
            eventList = eventsDB.GetEventList(memberList);
            eventsDB.close();
        }

        /*memberList.add(new Member("Ryan McLarty", "2021", "Trumpet", "testR"));
        memberList.add(new Member("Chiara Giammatteo", "2021", "Trumpet", "testC"));
        memberList.add(new Member("David Andreas", "2022", "Percussion", "testD"));
        memberList.add(new Member("Julia Baum", "2020", "Tenor Sax", "testJ"));

        memberList.add(new Member("Julia Baum2", "2020", "Tenor Sax", "testJ"));

        memberList.add(new Member("Julia Baum3", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum4", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum5", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum6", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum7", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum8", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum9", "2020", "Tenor Sax", "testJ"));
        memberList.add(new Member("Julia Baum0", "2020", "Tenor Sax", "testJ"));*/

        Calendar cal = Calendar.getInstance(); // This is now the only way to do M/D/Y in Java!
        cal.set(2020, 1, 1);
        eventList.add(new Event("Test", "Test", "Test", cal.getTime(), memberList));
        cal.set(2020, 0, 23);
        eventList.add(new Event("Test2", "Test2", "Test2", cal.getTime(), memberList));
        SortEventDates();

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

        if (id == R.id.nav_schedule)
        {
            intent = new Intent(getApplicationContext(), EventsFragment.class);
            //startActivity(new Intent(getApplicationContext(), ))
        } else if (id == R.id.nav_attendance)
        {
            intent = new Intent(getApplicationContext(), AttendanceFragment.class);
        } else if (id == R.id.nav_members)
        {
            intent = new Intent(getApplicationContext(), MembersFragment.class);
        }
        if (intent != null) {
            intent.putExtra("currentlyDisplayedEvent", eventList.get(0));
            intent.putParcelableArrayListExtra("eventList", eventList);
            intent.putParcelableArrayListExtra("memberList", memberList);
            intent.putExtra("SELECTED_DRAWER_ITEM", menuItem.getItemId());
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        finish();
        return true;
    }

    void GetIntentExtras(Intent intent)
    {
        currentlyDisplayedEvent = intent.getParcelableExtra("currentlyDisplayedEvent");
        eventList = intent.getParcelableArrayListExtra("eventList");
        memberList = intent.getParcelableArrayListExtra("memberList");
        selectedMenuID = intent.getIntExtra("SELECTED_DRAWER_ITEM", -1);
    }
}
