package com.helenpaulini.ribbon_resources;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helenpaulini.ribbon_resources.models.Post;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ConnectedProfileAdapter extends RecyclerView.Adapter<ConnectedProfileAdapter.ViewHolder> {

    public interface OnConnectedDetailsClickListener {
        void OnDetailsClicked(int position);
    }

    public static final String TAG = "ConnectedProfs";
    Context context;
    List<Profile> profiles = new ArrayList<>();
    OnConnectedDetailsClickListener onDetailsClickListener;

    public ConnectedProfileAdapter(Context context, OnConnectedDetailsClickListener onDetailsClickListener, List<Profile> profiles){
        this.context = context;
        this.profiles = profiles;
        this.onDetailsClickListener = onDetailsClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View profileView = LayoutInflater.from(context).inflate(R.layout.item_connecteduser, parent, false);
        return new ConnectedProfileAdapter.ViewHolder(profileView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + position);
        Profile profile = profiles.get(position);
        try {
            holder.bind(profile);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        profiles.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Profile> list) {
        profiles.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvConnectedFirstName;
        private TextView tvConnectedLastName;
        private TextView tvConnectedUserType;
        private TextView tvConnectedCancerType;
        private TextView tvConnectedHospital;
        private ImageView ivConnectedProfilePic;
        private Button btnConnectedSave;
        private Button btnConnectedDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConnectedFirstName = itemView.findViewById(R.id.tvConnectedFirstName);
            tvConnectedLastName = itemView.findViewById(R.id.tvConnectedLastName);
            tvConnectedUserType = itemView.findViewById(R.id.tvConnectedUserType);
            tvConnectedCancerType = itemView.findViewById(R.id.tvConnectedCancerType);
            tvConnectedHospital = itemView.findViewById(R.id.tvConnectedHospital);
            ivConnectedProfilePic = itemView.findViewById(R.id.ivConnectedProfilePic);
            btnConnectedDetails = itemView.findViewById(R.id.btnConnectedDetails);
            btnConnectedSave = itemView.findViewById(R.id.btnConnectedSave);
        }

        public void bind(Profile profile) throws com.parse.ParseException {
            tvConnectedFirstName.setText(profile.getFirstName());
            tvConnectedLastName.setText(profile.getLastName());
            tvConnectedUserType.setText(profile.getUserType());
            tvConnectedCancerType.setText(profile.getCancerType());
            tvConnectedHospital.setText(profile.getHospital());

            btnConnectedSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveUserRelation(profile);
                }
            });

            ParseFile image = profile.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivConnectedProfilePic);
            }

            btnConnectedDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDetailsClickListener.OnDetailsClicked(getAdapterPosition());
                }
            });
        }

        public void saveUserRelation(Profile clickedProfile) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();
            try {
                currentUser.fetchIfNeeded().getRelation("userRelation").add(clickedProfile.getUser());

                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "My user relations saved successfully!!");
                    }
                });
                Log.i(TAG, "saved user relations**");
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
