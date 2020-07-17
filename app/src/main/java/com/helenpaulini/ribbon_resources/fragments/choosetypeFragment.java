package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.helenpaulini.ribbon_resources.FragmentTabsAdapter;
import com.helenpaulini.ribbon_resources.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link choosetypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class choosetypeFragment extends Fragment {

    public static final String TAG = "ChoosetypeFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private CheckBox cbCurrentPatient;
    private CheckBox cbCancerSurvivor;
    private Spinner sprCancerTypeDropdown;
    private Button btnSave;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public choosetypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment choosetypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static choosetypeFragment newInstance(String param1, String param2) {
        choosetypeFragment fragment = new choosetypeFragment();
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
        return inflater.inflate(R.layout.fragment_choosetype, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(new FragmentTabsAdapter(getChildFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = view.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        cbCurrentPatient = view.findViewById(R.id.cbCurrentPatient);
        cbCancerSurvivor = view.findViewById(R.id.cbCancerSurvivor);
        sprCancerTypeDropdown = view.findViewById(R.id.sprCancerTypeDropdown);
        btnSave = view.findViewById(R.id.btnSave);
    }
}