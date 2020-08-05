package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.helenpaulini.ribbon_resources.PagerAdapter;
import com.helenpaulini.ribbon_resources.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewpagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewpagerFragment extends Fragment {

    public static final String TAG = "Viewpager";

    private TabLayout tabBar;
    private TabItem profiletab1;
    private TabItem profiletab2;
    private TabItem profiletab3;
    private ViewPager viewPager;
    PagerAdapter pagerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewpagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewpagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewpagerFragment newInstance(String param1, String param2) {
        ViewpagerFragment fragment = new ViewpagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabBar = view.findViewById(R.id.tabBar);

        profiletab1 = view.findViewById(R.id.profiletab1);
        profiletab2 = view.findViewById(R.id.profiletab2);
        profiletab3 = view.findViewById(R.id.profiletab3);
        viewPager = view.findViewById(R.id.tabsView);

        pagerAdapter = new PagerAdapter(getFragmentManager(), tabBar.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabBar.setupWithViewPager(viewPager);
        tabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

//                tabBar.getTabAt(tab.getPosition()).select();

//                tabBar.setScrollPosition(tab.getPosition(),0f,true);
//                viewPager.setCurrentItem(tab.getPosition());

//                TabLayout.Tab selectedTab = tabBar.getTabAt(tab.getPosition());
//                selectedTab.select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}