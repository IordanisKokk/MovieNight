package com.example.watch_together;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new homeFragment()).commit();
    }

    /**
     *
     * This is a navigationBar listener that listens to changes made on the navbar
     * It is specifically a OnItemSelectedListener and depending on the selected item
     * creates a new instance of the fragment that the user wants to select (homeFragment,
     * searchFragment, profileFragment) using a switch statement
     * Finally, it uses an object of the FragmentManager class to replace the current fragment with
     * the selected fragment.
     *
     */

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.homeFragment:
                    //code
                    selectedFragment = new homeFragment();
                    break;
                case R.id.searchFragment:
                    //code
                    selectedFragment = new searchFragment();
                    break;
                case R.id.profileFragment:
                    //code
                    selectedFragment = new profileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, selectedFragment).commit();
            return true;
        }
    };
}