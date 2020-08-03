package com.helenpaulini.ribbon_resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.helenpaulini.ribbon_resources.fragments.ConnectionsFragment;
import com.helenpaulini.ribbon_resources.fragments.MedicalinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.NameAndBioFragment;
import com.helenpaulini.ribbon_resources.fragments.PersonalinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.RequestconnectionFragment;

public class ConnectionPagerAdapter extends FragmentPagerAdapter{

    private int numOfTabs;

    public ConnectionPagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new RequestconnectionFragment();
            case 1:
                return new ConnectionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
