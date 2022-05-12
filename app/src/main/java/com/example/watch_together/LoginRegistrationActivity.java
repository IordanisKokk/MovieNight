package com.example.watch_together;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LoginRegistrationActivity extends AppCompatActivity {
    /*For now it is always true*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        BottomNavigationView bottomNav = findViewById(R.id.registerBottomNavigationView);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new loginFragment()).commit();


    }

    /**
     *
     * This is a navigationBar listener that listens to changes made on the navbar
     * It is specifically a OnItemSelectedListener and depending on the selected item
     * creates a new instance of the fragment that the user wants to select (loginFragment,
     * registerFragment) using a switch statement
     * Finally, it uses an object of the FragmentManager class to replace the current fragment with
     * the selected fragment.
     *
     */

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.loginFragment:
                    //code
                    selectedFragment = new loginFragment();
                    break;
                case R.id.registerFragment:
                    //code
                    selectedFragment = new registerFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, selectedFragment).commit();
            return true;
        }
    };


    /**
     * This method is called from inside of the two fragment classes (loginFragment and registerFragment) in order
     * to create a new Intent and start the MainActivity activity.
     * It gets called when the user is logged in, or when the user registers a new account.
     */
    public void makeMainActivityIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}