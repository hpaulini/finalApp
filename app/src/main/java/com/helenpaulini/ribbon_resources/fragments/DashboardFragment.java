package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.helenpaulini.ribbon_resources.ProfileAdapter;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.MyConnections;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.utilities.Matching;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    public static final String TAG = "Dashboard fragment";

    private String client;
    private RecyclerView rvDashboard;
    private SearchView svFindUsers;
    private Spinner spFilterMatches;

    protected ProfileAdapter adapter;
    protected List<Profile> profiles;
    ProfileAdapter.OnDetailsClickListener onDetailsClickListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDashboard = view.findViewById(R.id.rvDashboard);
        spFilterMatches = view.findViewById(R.id.spFilterMatches);

        String[] filtersMenu = getResources().getStringArray(R.array.filters_menu);
        List<String> filtersMenuArray = Arrays.asList(filtersMenu);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, filtersMenuArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterMatches.setAdapter(arrayAdapter);

        svFindUsers = view.findViewById(R.id.svFindUsers);

        svFindUsers.setImeOptions(EditorInfo.IME_ACTION_DONE);

        svFindUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        onDetailsClickListener = new ProfileAdapter.OnDetailsClickListener() {
            @Override
            public void OnDetailsClicked(int position) {
                goToDetailView(position);
            }
        };

        profiles = new ArrayList<>();

        onDetailsClickListener = new ProfileAdapter.OnDetailsClickListener() {
            @Override
            public void OnDetailsClicked(int position) {
                goToDetailView(position);
            }
        };

        adapter = new ProfileAdapter(getContext(), onDetailsClickListener, profiles);
        rvDashboard.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvDashboard.setLayoutManager(linearLayoutManager);
        queryProfiles();
    }

    protected void queryProfiles() {
        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        query.include(Profile.KEY_USER);
        query.findInBackground(new FindCallback<Profile>() {
            @Override
            public void done(List<Profile> profilesList, ParseException e) {
                //profilesList = profileMatches(getCurrentProfile(ParseUser.getCurrentUser(), profilesList), profilesList);
                if (e != null) {
                    Log.e(TAG, "Issue with getting profiles", e);
                    return;
                }
                for (Profile profile : profilesList) {
                    Log.i(TAG, "Username: " + profile.getUser().getUsername());
                }
                adapter.clear();
                adapter.addAll(profilesList);
//                profiles.addAll(profilesList);
//                adapter.notifyDataSetChanged();
            }
        });
    }

//    private Profile getCurrentProfile(){
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        Profile currentProfile = null;
//        try {
//            currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return currentProfile;
//    }

    private Profile getCurrentProfile(ParseUser user, List<Profile> allProfiles){
        int indexOfCurrentProfile=0;
        for(int i=0; i<allProfiles.size(); i++){
            if (allProfiles.get(i).getUser().getObjectId().equals(user.getObjectId())){
                indexOfCurrentProfile = i;
            }
        }
        Log.i(TAG, "current user: " + allProfiles.get(indexOfCurrentProfile).getUser().getUsername());
        return allProfiles.get(indexOfCurrentProfile);
    }

    private List<Profile> profileMatches (Profile currentProfile, List<Profile> profiles){
        List<Profile> profileMatches = new ArrayList<>();
        Matching potentialmatch = new Matching();
        for(int i=0; i<profiles.size(); i++){

            Log.i(TAG, "match value between "+ currentProfile.getUser().getUsername()
                    + " and "+ profiles.get(i).getUser().getUsername()+" is "+potentialmatch.matchValue(currentProfile, profiles.get(i)));

            if(potentialmatch.matchValue(currentProfile, profiles.get(i))>0){
                profileMatches.add(profiles.get(i));
            }
        }
        return profileMatches;
    }

    private void goToDetailView(int position){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        UserdetailsFragment userdetailsFragment = new UserdetailsFragment();
        Profile profile = profiles.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("profileDetails", profile);
        userdetailsFragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.flContainer, userdetailsFragment).addToBackStack(null).commit();
    }
}