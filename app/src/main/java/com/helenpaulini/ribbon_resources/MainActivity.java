package com.helenpaulini.ribbon_resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.helenpaulini.ribbon_resources.fragments.ConnectionsFragment;
import com.helenpaulini.ribbon_resources.fragments.ContactinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.DashboardFragment;
import com.helenpaulini.ribbon_resources.fragments.FindusersFragment;
import com.helenpaulini.ribbon_resources.fragments.PersonalinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.ProfileFragment;
import com.helenpaulini.ribbon_resources.fragments.ResourcesFragment;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private TabLayout tabLayout;
    private TabItem profiletab1;
    private TabItem profiletab2;
    private TabItem profiletab3;
    private ViewPager viewPager;
    PagerAdapter pagerAdapter;
    private ProfileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabBar);
        profiletab1 = findViewById(R.id.profiletab1);
        profiletab2 = findViewById(R.id.profiletab2);
        profiletab3 = findViewById(R.id.profiletab3);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                    default:
                        //Toast.makeText(MainActivity.this, "profile!", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "ONE");
        adapter.addFragment(new ContactinfoFragment(), "TWO");
        adapter.addFragment(new PersonalinfoFragment(), "THREE");
        viewPager.setAdapter(adapter);
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}