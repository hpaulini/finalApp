package com.helenpaulini.ribbon_resources.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComposepostdialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposepostdialogFragment extends DialogFragment {

    public static final String TAG = "Compose dialog fragment";

    private TextInputEditText postTitle;
    private TextInputEditText postBody;
    private TextInputEditText postTags;
    private Button btnSubmitPost;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComposepostdialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComposepostdialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComposepostdialogFragment newInstance(String param1, String param2) {
        ComposepostdialogFragment fragment = new ComposepostdialogFragment();
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
        return inflater.inflate(R.layout.fragment_composepostdialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postTitle = view.findViewById(R.id.postTitle);
        postBody = view.findViewById(R.id.postBody);
        postTags = view.findViewById(R.id.postTags);
        btnSubmitPost = view.findViewById(R.id.btnSubmitPost);

        postTitle.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = postTitle.getText().toString();
                String body = postBody.getText().toString();
                String tags = postTags.getText().toString();

                if (body.isEmpty() || title.isEmpty()) {
                    Toast.makeText(getContext(), "Title and body text cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(title, body, tags, currentUser);
            }
        });
    }

    private void savePost(String title, String body, String tags, ParseUser currentUser) {
        Post post = new Post();
        post.setHeader(title);
        post.setDescription(body);
        post.setTags(tags);
        post.setUser(currentUser);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post saved successfully!!");
                postTitle.setText("");
                postBody.setText("");
                postTags.setText("");
            }
        });
    }
}