package com.example.darkknight.ait_20.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;

import com.example.darkknight.ait_20.Fragments.Login.Login;
import com.example.darkknight.ait_20.R;

import static com.example.darkknight.ait_20.Utils.Utils.IS_LOGGEDIN;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_ROLLNO;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences wwz_SP = PreferenceManager.getDefaultSharedPreferences(this);
        final Boolean is_loggedin =wwz_SP.getBoolean(IS_LOGGEDIN, false);

        if(is_loggedin){
            Intent homescreen_intent = new Intent(LoginActivity.this,HomeScreen.class);
            startActivity(homescreen_intent);
        }
        else {
            Login fragobj = new Login();
            getFragmentManager().beginTransaction().replace(R.id.login_xll, fragobj).commit();
        }
        }

    @Override
    public void onBackPressed () {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }
        else if(getFragmentManager().getBackStackEntryCount()==0){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}