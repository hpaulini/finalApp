package com.helenpaulini.ribbon_resources;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.fragments.ContactinfoFragment;
import com.helenpaulini.ribbon_resources.fragments.UserdetailsFragment;
import com.helenpaulini.ribbon_resources.models.MyConnections;
import com.helenpaulini.ribbon_resources.models.Post;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> implements Filterable {

    public interface OnDetailsClickListener{
        void OnDetailsClicked(int position);
    }

    public static final String TAG = "ProfileAdapter";
    Context context;
    OnDetailsClickListener onDetailsClickListener;
    List<Profile> profiles;
    List<Profile> profileListFull;
    List<Profile> myConnections = new ArrayList<>();

    public ProfileAdapter(Context context, OnDetailsClickListener onDetailsClickListener, List<Profile> profiles) {
        this.context = context;
        this.onDetailsClickListener = onDetailsClickListener;
        this.profiles = profiles;
        profileListFull = new ArrayList<>(profiles);
        //profileListFull = profiles;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View profileView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(profileView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + position);
        // Get the movie at the position
        Profile profile = profiles.get(position);
        //Bind the movie data into the view holder
        holder.bind(profile);
    }

    // Returns the total count of the items in the list
    @Override
    public int getItemCount() {
        return profiles.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        profiles.clear();
        profileListFull.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Profile> list) {
        profiles.addAll(list);
        profileListFull.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return profileFilter;
    }

    private Filter profileFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Profile> filteredList = new ArrayList<>();
            Log.i(TAG, "What user typed: "+constraint);
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(profileListFull);
            } else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                Log.i(TAG, "in else"+profileListFull.size());
                for (int i=0; i<profileListFull.size(); i++){
                    Log.i(TAG, "In for loop");
                    if((profileListFull.get(i).getFirstName().toLowerCase().trim()).contains(filterPattern)
                    || (profileListFull.get(i).getLastName().toLowerCase().trim()).contains(filterPattern)
                    || (profileListFull.get(i).getHospital().toLowerCase().trim()).contains(filterPattern)
                    || (profileListFull.get(i).getCancerType().toLowerCase().trim()).contains(filterPattern)
                    || (profileListFull.get(i).getUserType().toLowerCase().trim()).contains(filterPattern)){
                        filteredList.add(profileListFull.get(i));
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            profiles.clear();
            profiles.addAll((List<Profile>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private TextView tvFirstName;
        private TextView tvLastName;
        private TextView tvCity;
        private TextView tvAge; //To do: calculate age based on birthday
        private TextView tvUserType;
        private TextView tvCancerType;
        private TextView tvHospital;
        private ImageView ivProfilePic;
        private Button btnNotification;
        private Button btnConnect;
        private Button btnSave;
        private Button btnUserDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvUserType = itemView.findViewById(R.id.tvUserType);
            tvCancerType = itemView.findViewById(R.id.tvCancerType);
            tvHospital = itemView.findViewById(R.id.tvHospital);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            btnConnect = itemView.findViewById(R.id.btnConnect);
            btnSave = itemView.findViewById(R.id.btnSave);
            btnUserDetails = itemView.findViewById(R.id.btnUserDetails);

            itemView.setOnClickListener(this);
        }

        public void bind(final Profile profile) {
            tvFirstName.setText(profile.getFirstName());
            tvLastName.setText(profile.getLastName());
            tvUserType.setText(profile.getUserType());
            tvCancerType.setText(profile.getCancerType());
            tvHospital.setText(profile.getHospital());

            btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connectWithUser(profile);
                }
            });

            //to do: put my connections stuff in its own class where it makes an array list containing the saved connections
            //then, in profile (or connections??) fragment, make a new object of that class and send the list to the parse database for the current user
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveUserRelation(profile);
                    //addToProfileArray(profile);
                    //saveConnectionsRelation(profile);
                    //onSaveConnectionClick(profile);
                }
            });

            ParseFile image = profile.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivProfilePic);
            }

            btnUserDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDetailsClickListener.OnDetailsClicked(getAdapterPosition());
                }
            });
        }

        public String age (Date birthday){
            String dobString = birthday.toString();
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = sdf.parse(dobString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(date == null) {
                return "0";
            }

            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.setTime(date);

            int year = dob.get(Calendar.YEAR);
            int month = dob.get(Calendar.MONTH);
            int day = dob.get(Calendar.DAY_OF_MONTH);

            dob.set(year, month+1, day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }
            return ""+age;
        }

        public void goToDetails(){

        }

        @Override
        public void onClick(View v) {
//            // gets item position
//            int position = getAdapterPosition();
//            // make sure the position is valid, i.e. actually exists in the view
//            if (position != RecyclerView.NO_POSITION) {
//                SurvivorProfile sProfile = sProfiles.get(position);
//                // create intent for the new activity
//                Intent intent = new Intent(context, ProfileDetails.class);
//                // serialize the movie using parceler, use its short name as a key
//                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(sProfile));
//                // show the activity
//                context.startActivity(intent);
//            }
        }

        public void connectWithUser(Profile clickedProfile){

        }

        public void saveUserRelation(Profile clickedProfile){
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
                        Log.i(TAG, "My user relaitons saved successfully!!");
                    }
                });
                Log.i(TAG, "saved user relations**");
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }

        public void addToProfileArray(Profile clickedProfile){
            myConnections.add(clickedProfile);
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();

            try {
                Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");
                currentProfile.setProfileArray(myConnections);

                currentProfile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "My connections array saved successfully!!");
                    }
                });
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }

        public void saveConnectionsRelation(Profile clickedProfile){
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();
            try {
                Profile currentProfile = (Profile) currentUser.fetchIfNeeded().getParseObject("profile");
                Log.i(TAG, "clicked profile: "+clickedProfile.getFirstName());
                Log.i(TAG, "current profile: "+(Profile) currentUser.fetchIfNeeded().getParseObject("profile"));
                currentProfile.fetchIfNeeded().getRelation("profileRelation").add(clickedProfile);

                currentProfile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "My connections model saved successfully!!");
                    }
                });
                Log.i(TAG, "saved profiles**");
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }



        public void onSaveConnectionClick(Profile clickedProfile){

            //add the clicked profile item to the current user's myConnections arraylist
            myConnections.add(clickedProfile);

            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.fetchInBackground();
            Log.i(TAG, "who is currently logged in: "+currentUser.getUsername());
            MyConnections connections;

            try {
                if(currentUser.fetchIfNeeded().getParseObject("myConnections")==null) {
                    connections = new MyConnections();
                } else{
                    connections = (MyConnections) currentUser.fetchIfNeeded().getParseObject("myConnections");
                    Log.i(TAG, "previous connections: "+connections.getMyConnections());
                }

                connections.setUser(currentUser);
                connections.setMyConnections(myConnections);

                connections.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "My connections model saved successfully!!");
                    }
                });
                currentUser.put("myConnections", connections);
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                    }
                });

                Log.i(TAG, "current connections: "+connections.getMyConnections());

            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }
    }
}