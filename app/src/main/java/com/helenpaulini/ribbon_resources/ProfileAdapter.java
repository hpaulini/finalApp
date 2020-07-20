package com.helenpaulini.ribbon_resources;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helenpaulini.ribbon_resources.R;
import com.helenpaulini.ribbon_resources.models.Post;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.helenpaulini.ribbon_resources.models.SurvivorProfile;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    public static final String TAG = "ProfileAdapter";
    Context context;
    List<Profile> profiles;

    public ProfileAdapter(Context context, List<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
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
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Profile> list) {
        profiles.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private TextView tvFirstName;
        private TextView tvLastName;
        private TextView tvCity;
        private TextView tvAge;
        private TextView tvUserType;
        private TextView tvCancerType;
        private TextView tvHospital;
        private ImageView ivProfilePic;

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

            itemView.setOnClickListener(this);
        }

        public void bind(Profile profile) {
            tvUsername.setText(profile.getUser().getUsername());
            tvFirstName.setText(profile.getFirstName());
            tvLastName.setText(profile.getLastName());
            tvCity.setText(profile.getCity());
            tvUserType.setText("Childhood Cancer Survivor");
            tvCancerType.setText(profile.getCanerType());
            tvHospital.setText(profile.getHospital());

            ParseFile image = profile.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivProfilePic);
            }
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
    }
}