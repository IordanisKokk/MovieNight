package com.example.watch_together;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LoginRegistrationActivity extends AppCompatActivity {
    /*For now it is always true*/
    boolean userIsLoggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(userIsLoggedIn){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        BottomNavigationView bottomNav = findViewById(R.id.registerBottomNavigationView);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new loginFragment()).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.loginFragment:
                    //code
                    selectedFragment = new loginFragment();
                    break;
                case R.id.registerFragment:r:
                    //code
                    selectedFragment = new registerFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, selectedFragment).commit();
            return true;
        }
    };

}