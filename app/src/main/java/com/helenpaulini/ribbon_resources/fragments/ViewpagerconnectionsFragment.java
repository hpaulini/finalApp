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
import com.helenpaulini.ribbon_resources.ConnectionPagerAdapter;
import com.helenpaulini.ribbon_resources.PagerAdapter;
import com.helenpaulini.ribbon_resources.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewpagerconnectionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewpagerconnectionsFragment extends Fragment {

    public static final String TAG = "Viewpager";

    private TabLayout connectionTabBar;
    private TabItem connectiontab1;
    private TabItem connectiontab2;
    private ViewPager viewPager;
    ConnectionPagerAdapter pagerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewpagerconnectionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewpagerconnectionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewpagerconnectionsFragment newInstance(String param1, String param2) {
        ViewpagerconnectionsFragment fragment = new ViewpagerconnectionsFragment();
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
        return inflater.inflate(R.layout.fragment_viewpagerconnections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectionTabBar = view.findViewById(R.id.connectionTabBar);

        connectiontab1 = view.findViewById(R.id.connectiontab1);
        connectiontab2 = view.findViewById(R.id.connectiontab2);
        viewPager = view.findViewById(R.id.connectionTabsView);

        pagerAdapter = new ConnectionPagerAdapter(getFragmentManager(), connectionTabBar.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        connectionTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }
}