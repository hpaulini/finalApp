package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.helenpaulini.ribbon_resources.MainActivity;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Hospital;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchablespinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchablespinnerFragment extends Fragment {

    public static final String API_URL = "https://services1.arcgis.com/Hp6G80Pky0om7QvQ/arcgis/rest/services/Hospitals_1/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json";
    public static final String TAG = "Searchable spinner";

    List<Hospital> hospitals = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    SpinnerDialog spinnerDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchablespinnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchablespinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchablespinnerFragment newInstance(String param1, String param2) {
        SearchablespinnerFragment fragment = new SearchablespinnerFragment();
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
        return inflater.inflate(R.layout.fragment_searchablespinner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView selectedItems = view.findViewById(R.id.txt);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onsuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray features = jsonObject.getJSONArray("features");
                    hospitals = Hospital.fromJsonArray(features); //hospitals is a list of [{key:{key1:value, key2:value, ...}}, ...]
                    items = hospitalNameList(hospitals);
                    spinnerDialog = new SpinnerDialog(getActivity(), hospitalNameList(hospitals),
                            "Select or Search Hospitals");
                    Log.i(TAG, "Items size "+items.size());

                    spinnerDialog.setTitleColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setSearchIconColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setSearchTextColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setItemColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setItemDividerColor(getResources().getColor(R.color.pink));
                    spinnerDialog.setCloseColor(getResources().getColor(R.color.pink));

                    spinnerDialog.setCancellable(true);
                    spinnerDialog.setShowKeyboard(false);

                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            Toast.makeText(getContext(), item + "  " + position + "", Toast.LENGTH_SHORT).show();
                            selectedItems.setText(item + " Position: " + position);
                        }
                    });

                    view.findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            spinnerDialog.showSpinerDialog();
                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "json exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onfailure", throwable);
            }
        });
    }

    private ArrayList<String> hospitalNameList(List<Hospital> hospitals){
        ArrayList<String> hospitalNameList = new ArrayList<>();
        Log.i(TAG, "array length: "+hospitals.size());
        for(int i=0; i<hospitals.size(); i++){
            hospitalNameList.add(hospitals.get(i).getName());
        }
        Log.i(TAG, "returned length: "+hospitalNameList.size());
        return hospitalNameList;
    }
}