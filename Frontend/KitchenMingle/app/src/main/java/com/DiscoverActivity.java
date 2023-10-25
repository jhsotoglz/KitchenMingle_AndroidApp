package com;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

public class DiscoverActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Handle Home page navigation
                        // Example: startActivity(new Intent(com.DiscoverActivity.this, HomeActivity.class));
                        return true;
                    case R.id.navigation_discover:
                        // Handle Discover page navigation (you're already on this page)
                        return true;
                    case R.id.navigation_profile:
                        // Handle Profile page navigation
                        // Example: startActivity(new Intent(com.DiscoverActivity.this, ProfileActivity.class));
                        return true;
                }
                return false;
            }
        });
    }
}