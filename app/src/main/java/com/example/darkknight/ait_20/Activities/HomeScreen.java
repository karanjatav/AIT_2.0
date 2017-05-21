package com.example.darkknight.ait_20.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.darkknight.ait_20.Fragments.Login.Login;
import com.example.darkknight.ait_20.Fragments.Main.Contact;
import com.example.darkknight.ait_20.Fragments.Main.InfoTab;
import com.example.darkknight.ait_20.Fragments.Main.NewsFeed;

import com.example.darkknight.ait_20.Fragments.Main.Results;
import com.example.darkknight.ait_20.Fragments.Main.Scheduler;
import com.example.darkknight.ait_20.R;

import static com.example.darkknight.ait_20.Utils.Utils.IS_LOGGEDIN;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_EMAIL;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_NAME;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_ROLLNO;


public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView username_nav_tv,useremail_nav_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        SharedPreferences wwz_SP = PreferenceManager.getDefaultSharedPreferences(HomeScreen.this);
         String student_name =wwz_SP.getString(STUDENT_NAME, "no name");
         String student_email =wwz_SP.getString(STUDENT_EMAIL, "no email");

        Log.d("karan_homescreen","student_name=="+student_name);

      /*  username_nav_tv.setText(student_name);
        useremail_nav_tv.setText(student_email);*/


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        username_nav_tv = (TextView)findViewById(R.id.username_nav_tv);
        useremail_nav_tv = (TextView)findViewById(R.id.useremail_nav_tv);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);

        TextView username_nav_tv = (TextView) header.findViewById(R.id.username_nav_tv);
        username_nav_tv.setText(student_name);
        TextView useremail_nav_tv = (TextView) header.findViewById(R.id.useremail_nav_tv);
        useremail_nav_tv.setText(student_email);



        navigationView.setNavigationItemSelectedListener(this);


        NewsFeed fragobj = new NewsFeed();
        getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).commit();



    }


    @Override
    public void onBackPressed() {
        Log.d("karan_homeScreen","backStackEntry=="+getFragmentManager().getBackStackEntryCount());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getFragmentManager().getBackStackEntryCount()>0){
                getFragmentManager().popBackStack();
            }
            else if(getFragmentManager().getBackStackEntryCount()==0){
                Log.d("karan_homeScreen","Karan=="+getFragmentManager().getBackStackEntryCount());
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences k_SP = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = k_SP.edit();
            editor.putBoolean(IS_LOGGEDIN, false);
            editor.apply();

            Intent login_intent = new Intent(HomeScreen.this,LoginActivity.class);
            startActivity(login_intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            NewsFeed fragobj = new NewsFeed();
            getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).commit();
        } else if (id == R.id.nav_gallery) {
            Results fragobj = new Results();
            getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).addToBackStack(null).commit();
        } else if (id == R.id.nav_slideshow) {
            Scheduler fragobj = new Scheduler();
            getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).addToBackStack(null).commit();

        } else if (id == R.id.nav_info) {
            InfoTab fragobj = new InfoTab();
            getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).addToBackStack(null).commit();

        }  else if (id == R.id.nav_send) {
            Contact fragobj = new Contact();
            getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).addToBackStack(null).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
 public void goToResults(){
     Results fragobj = new Results();
     getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).addToBackStack(null).commit();
 }
    public void goToScheduler(){
        Scheduler fragobj = new Scheduler();
        getFragmentManager().beginTransaction().replace(R.id.homeScreen_rl, fragobj).addToBackStack(null).commit();
    }


}
