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
import com.helenpaulini.ribbon_resources.models.RequestedConnections;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestconnectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestconnectionFragment extends Fragment {
    public static final String TAG = "Request connection fragment";

    private String client;
    private RecyclerView rvMyConnections;
    private RecyclerView rvRequestedConnections;
    private RecyclerView rvPendingConnections;
    protected ProfileAdapter myConnectionsAdapter, requestedConnectionsAdapter, pendingConnectionsAdapter;
    protected List<Profile> profiles;
    List<Profile> profilesFromParseObject;
    ProfileAdapter.OnDetailsClickListener onDetailsClickListener;

    private List<Profile> requestedProfiles = new ArrayList<>();
    private List<Profile> pendingProfiles = new ArrayList<>();
    private List<Profile> acceptedProfiles = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestconnectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestconnectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestconnectionFragment newInstance(String param1, String param2) {
        RequestconnectionFragment fragment = new RequestconnectionFragment();
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
        return inflater.inflate(R.layout.fragment_requestconnection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMyConnections = view.findViewById(R.id.rvMyConnections);
        rvRequestedConnections = view.findViewById(R.id.rvRequestedConnections);
        rvPendingConnections = view.findViewById(R.id.rvPendingConnections);
        profiles = new ArrayList<>();

        onDetailsClickListener = new ProfileAdapter.OnDetailsClickListener() {
            @Override
            public void OnDetailsClicked(int position) {
                goToDetailView(position);
            }
        };

        //create the adapter
        pendingConnectionsAdapter = new ProfileAdapter(getContext(), onDetailsClickListener, pendingProfiles);
        //set the adapter on the recycler view
        rvPendingConnections.setAdapter(pendingConnectionsAdapter);
        //set the layout on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPendingConnections.setLayoutManager(linearLayoutManager);
        //queryPendingProfiles();
        queryConnectionsRequested();

        //create the adapter
        requestedConnectionsAdapter = new ProfileAdapter(getContext(), onDetailsClickListener, requestedProfiles);
        //set the adapter on the recycler view
        rvRequestedConnections.setAdapter(requestedConnectionsAdapter);
        //set the layout on the recycler view
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        rvRequestedConnections.setLayoutManager(linearLayoutManager3);
        //queryRequestedProfiles();
        queryConnectionRequests();

        //create the adapter
        myConnectionsAdapter = new ProfileAdapter(getContext(), onDetailsClickListener, acceptedProfiles);
        //set the adapter on the recycler view
        rvMyConnections.setAdapter(myConnectionsAdapter);
        //set the layout on the recycler view
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        rvMyConnections.setLayoutManager(linearLayoutManager2);
        //queryConnectedProfiles();
        queryConnectionsAccepted();
    }

    public void queryConnectionRequests(){
        try {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();
            Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");

            ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
            query.include(Profile.KEY_USER);
            final List<ParseUser> requestorProfiles = new ArrayList<>();
            final ParseQuery<ParseObject> profileRelation = currentProfile.getRelation("requestorProfiles").getQuery();
            profileRelation.include("User");
            profileRelation.findInBackground(new FindCallback<ParseObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void done(List<ParseObject> profiles, ParseException e) {
                    for(ParseObject profile : profiles){
                        try {
                            Log.i("connectionrequest", "current user: "+ParseUser.getCurrentUser().getUsername()+", ppl who have requested current user: "+ profile.fetchIfNeeded().getString("firstName"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        requestorProfiles.add((ParseUser) profile.getParseUser("user"));
                    }
                    //savedUsers.add(ParseUser.getCurrentUser());
                    query.whereContainedIn(Profile.KEY_USER, requestorProfiles);
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
                                Log.i(TAG, "Profile username: "+profile.getUser().getUsername());
                            }
                            requestedConnectionsAdapter.clear();
                            requestedConnectionsAdapter.addAll(profilesList);
                        }
                    });
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    public void queryConnectionsRequested(){
        try {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();
            Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");

            ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
            query.include(Profile.KEY_USER);
            final List<ParseUser> requestedProfiles = new ArrayList<>();
            final ParseQuery<ParseObject> profileRelation = currentProfile.getRelation("requestedProfiles").getQuery();
            profileRelation.include("User");
            profileRelation.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> profiles, ParseException e) {
                    for(ParseObject profile : profiles){
                        try {
                            Log.i(TAG, "current user: "+ParseUser.getCurrentUser().getUsername()+", ppl who the current user has requested: "+ profile.fetchIfNeeded().getString("firstName"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        requestedProfiles.add((ParseUser) profile.getParseUser("user"));
                    }
                    //savedUsers.add(ParseUser.getCurrentUser());
                    query.whereContainedIn(Profile.KEY_USER, requestedProfiles);
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
                                Log.i(TAG, "connections requested Profile username: "+profile.getUser().getUsername());
                            }
                            pendingConnectionsAdapter.clear();
                            pendingConnectionsAdapter.addAll(profilesList);
                        }
                    });
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void queryConnectionsAccepted(){

    }

    protected void queryConnectedProfiles() {

        for(int i=0; i<pendingProfiles.size(); i++){
            for(int j=0; j<requestedProfiles.size(); j++){
                if(pendingProfiles.get(i).equals(requestedProfiles.get(j))){
                    //to do: remove duplicate from pending and requested views, add it to the accepted connections
                    acceptedProfiles.add(pendingProfiles.get(i));
                    pendingProfiles.remove(i);
                    requestedProfiles.remove(j);
                }
            }
        }

        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        query.include(Profile.KEY_USER);
        query.findInBackground(new FindCallback<Profile>() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(List<Profile> profilesList, ParseException e) {
                profilesList = acceptedProfiles;
                if (e != null) {
                    Log.e(TAG, "Issue with getting profiles", e);
                    return;
                }
                for (Profile profile : profilesList) {
                    Log.i(TAG, "Username: " + profile.getUser().getUsername());
                }
                myConnectionsAdapter.clear();
                myConnectionsAdapter.addAll(profilesList);
            }
        });
    }

    protected void queryPendingProfiles() {
        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        query.include(Profile.KEY_USER);
        final List<ParseUser> savedUsers = new ArrayList<>();
        final ParseQuery<ParseObject> userRelation = ParseUser.getCurrentUser().getRelation("pendingConnectionRelation").getQuery();
        userRelation.include("User");
        userRelation.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> users, ParseException e) {
                for(ParseObject user : users){
                    savedUsers.add((ParseUser) user);
                }
                //savedUsers.add(ParseUser.getCurrentUser());
                query.whereContainedIn(Profile.KEY_USER, savedUsers);
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
                            Log.i(TAG, "Profile username: "+profile.getUser().getUsername());
                        }
                        pendingProfiles = profilesList;
                        pendingConnectionsAdapter.clear();
                        pendingConnectionsAdapter.addAll(profilesList);
                    }
                });
            }
        });
    }

    //to do: for some reason users appear in duplicates after requesting
    protected void queryRequestedProfiles() {
        ParseQuery<Profile> profileQuery = ParseQuery.getQuery(Profile.class);
        final List<ParseUser> requestedUsers = new ArrayList<>();
        profileQuery.include(Profile.KEY_USER);
        ParseQuery<RequestedConnections> connectionsQuery = ParseQuery.getQuery(RequestedConnections.class);
        connectionsQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        connectionsQuery.findInBackground(new FindCallback<RequestedConnections>() {
            @Override
            public void done(List<RequestedConnections> users, ParseException e) {
                for (RequestedConnections user : users) {
                    requestedUsers.add((ParseUser) user.getRequestedUser());
                }
                profileQuery.whereContainedIn(Profile.KEY_USER, requestedUsers);
                profileQuery.addDescendingOrder(Profile.KEY_CREATED_AT);
                profileQuery.findInBackground(new FindCallback<Profile>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void done(List<Profile> profilesList, ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Issue with getting profiles");
                            return;
                        }
                        for (Profile profile : profilesList) {
                            Log.i(TAG, "Profile username: " + profile.getUser().getUsername());
                        }
                        requestedProfiles = profilesList;
                        requestedConnectionsAdapter.clear();
                        requestedConnectionsAdapter.addAll(profilesList);
                    }
                });
            }
        });
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