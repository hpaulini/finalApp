package com.helenpaulini.ribbon_resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.helenpaulini.ribbon_resources.fragments.ConnectionsFragment;
import com.helenpaulini.ribbon_resources.fragments.DashboardFragment;
import com.helenpaulini.ribbon_resources.fragments.FindusersFragment;
import com.helenpaulini.ribbon_resources.fragments.ProfileFragment;
import com.helenpaulini.ribbon_resources.fragments.ResourcesFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                    default:
                        Toast.makeText(MainActivity.this, "dashboard!", Toast.LENGTH_SHORT).show();
                        fragment = new DashboardFragment();
                        break;
                    case R.id.profile:
                        Toast.makeText(MainActivity.this, "profile!", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    case R.id.myConnections:
                        Toast.makeText(MainActivity.this, "connections!", Toast.LENGTH_SHORT).show();
                        fragment = new ConnectionsFragment();
                        break;
                    case R.id.findUsers:
                        Toast.makeText(MainActivity.this, "findusers!", Toast.LENGTH_SHORT).show();
                        fragment = new FindusersFragment();
                        break;
                    case R.id.resources:
                        Toast.makeText(MainActivity.this, "resources!", Toast.LENGTH_SHORT).show();
                        fragment = new ResourcesFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
    }
}