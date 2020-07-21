package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helenpaulini.ribbon_resources.ProfileAdapter;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Post;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindusersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindusersFragment extends Fragment {

    public static final String TAG = "FindusersFragment";

    private String client;
    private RecyclerView rvFindUsers;
    protected ProfileAdapter adapter;
    protected List<Profile> profiles;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindusersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindusersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindusersFragment newInstance(String param1, String param2) {
        FindusersFragment fragment = new FindusersFragment();
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
        return inflater.inflate(R.layout.fragment_findusers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFindUsers = view.findViewById(R.id.rvFindUsers);
        profiles = new ArrayList<>();

        //create the adapter
        adapter = new ProfileAdapter(getContext(), profiles);
        //set the adapter on the recycler view
        rvFindUsers.setAdapter(adapter);
        //set the layout on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFindUsers.setLayoutManager(linearLayoutManager);
        queryProfiles();
    }

    protected void queryProfiles() {
        ParseQuery<Profile> query = ParseQuery.getQuery(Profile.class);
        query.include(Profile.KEY_USER);
        query.addDescendingOrder(Profile.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Profile>() {
            @Override
            public void done(List<Profile> profilesList, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting profiles", e);
                    return;
                }
                for (Profile profile : profilesList) {
                    Log.i(TAG, "Username: " + profile.getUser().getUsername());
                }
                profiles.addAll(profilesList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}