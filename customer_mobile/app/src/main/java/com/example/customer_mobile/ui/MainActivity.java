package com.example.customer_mobile.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.customer_mobile.R;
import com.example.customer_mobile.ui.fragments.HistoryPage;
import com.example.customer_mobile.ui.fragments.HomePage;
import com.example.customer_mobile.ui.fragments.MapPage;
import com.example.customer_mobile.ui.fragments.ProfilePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (savedInstanceState == null) {
            loadFragment(new HomePage());
        }
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomePage();
            } else if (item.getItemId() == R.id.map) {
                selectedFragment = new MapPage();
            } else if (item.getItemId() == R.id.history) {
                selectedFragment = new HistoryPage();
            } else if (item.getItemId() == R.id.user) {
                selectedFragment = new ProfilePage();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}