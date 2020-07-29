package com.helenpaulini.ribbon_resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.helenpaulini.ribbon_resources.fragments.ConnectionsFragment;
import com.helenpaulini.ribbon_resources.fragments.DashboardFragment;
import com.helenpaulini.ribbon_resources.fragments.FindusersFragment;
import com.helenpaulini.ribbon_resources.fragments.ProfileFragment;
import com.helenpaulini.ribbon_resources.fragments.ResourcesFragment;
import com.helenpaulini.ribbon_resources.fragments.ViewpagerFragment;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    private ProfileAdapter adapter;

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
                    case R.id.profile:
                    default:
                        //Toast.makeText(MainActivity.this, "profile!", Toast.LENGTH_SHORT).show();
                        fragment = new ViewpagerFragment();
                        break;
                    case R.id.dashboard:
                        //Toast.makeText(MainActivity.this, "dashboard!", Toast.LENGTH_SHORT).show();
                        fragment = new DashboardFragment();
                        break;
                    case R.id.myConnections:
                        //Toast.makeText(MainActivity.this, "connections!", Toast.LENGTH_SHORT).show();
                        fragment = new ConnectionsFragment();
                        break;
                    case R.id.findUsers:
                        //Toast.makeText(MainActivity.this, "findusers!", Toast.LENGTH_SHORT).show();
                        fragment = new FindusersFragment();
                        break;
                    case R.id.resources:
                        //Toast.makeText(MainActivity.this, "resources!", Toast.LENGTH_SHORT).show();
                        fragment = new ResourcesFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
            goLoginActivity();
            Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public BottomNavigationView getBottomNavigationView (){
        return bottomNavigationView;
    }
}