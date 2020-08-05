package com.helenpaulini.ribbon_resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.helenpaulini.ribbon_resources.fragments.ContactinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.MedicalinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.NameAndBioFragment;
import com.helenpaulini.ribbon_resources.fragments.PersonalinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.ProfileFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NameAndBioFragment();
            case 1:
                return new MedicalinfoFragment();
            case 2:
                return new PersonalinfoFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "Match Info.";
            case 2:
                return "Contact";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
