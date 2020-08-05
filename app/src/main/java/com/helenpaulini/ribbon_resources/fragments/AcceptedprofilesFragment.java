package com.helenpaulini.ribbon_resources.fragments;

import android.annotation.SuppressLint;
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

import com.helenpaulini.ribbon_resources.ProfileAdapter;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcceptedprofilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptedprofilesFragment extends Fragment {

    public static final String TAG = "Acceptedprofs";

    private String client;
    private RecyclerView rvMyConnections;
    protected List<Profile> profiles;
    ProfileAdapter adapter;
    ProfileAdapter.OnDetailsClickListener onDetailsClickListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AcceptedprofilesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcceptedprofilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcceptedprofilesFragment newInstance(String param1, String param2) {
        AcceptedprofilesFragment fragment = new AcceptedprofilesFragment();
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
        return inflater.inflate(R.layout.fragment_acceptedprofiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMyConnections = view.findViewById(R.id.rvMyConnections);
        profiles = new ArrayList<>();

        onDetailsClickListener = new ProfileAdapter.OnDetailsClickListener() {
            @Override
            public void OnDetailsClicked(int position) {
                goToDetailView(position);
            }
        };

        adapter = new ProfileAdapter(getContext(), onDetailsClickListener, profiles);
        rvMyConnections.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvMyConnections.setLayoutManager(linearLayoutManager);
        queryAcceptedConnections();
    }

    public void queryAcceptedConnections(){
        try {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();
            Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");

            ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
            query.include(Profile.KEY_USER);
            final List<ParseUser> acceptedProfiles = new ArrayList<>();
            final ParseQuery<ParseObject> profileRelation = currentProfile.getRelation("acceptedProfiles").getQuery();
            profileRelation.include("User");
            profileRelation.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> profiles, ParseException e) {
                    for(ParseObject profile : profiles){
                        try {
                            Log.i(TAG, "current user: "+ParseUser.getCurrentUser().getUsername()+", accepted requests: "+ profile.fetchIfNeeded().getString("firstName"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        acceptedProfiles.add((ParseUser) profile.getParseUser("user"));
                    }
                    //savedUsers.add(ParseUser.getCurrentUser());
                    query.whereContainedIn(Profile.KEY_USER, acceptedProfiles);
                    query.addDescendingOrder(Profile.KEY_CREATED_AT);
                    query.findInBackground(new FindCallback<Profile>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void done(List<Profile> profilesList, ParseException e) {
                            if(e!=null){
                                Log.e(TAG, "Issue with getting profiles");
                                return;
                            }
                            for(Profile profile:profilesList){
                                Log.i(TAG, "connections accepted Profile username: "+profile.getUser().getUsername());
                            }
                            adapter.clear();
                            adapter.addAll(profilesList);
                        }
                    });
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
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