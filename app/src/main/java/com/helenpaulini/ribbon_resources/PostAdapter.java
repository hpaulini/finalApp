package com.helenpaulini.ribbon_resources;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.helenpaulini.ribbon_resources.models.Post;
import com.helenpaulini.ribbon_resources.models.Profile;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public static final String TAG = "PostsAdapter";
    Context context;
    List<Post> posts;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View postView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + position);
        // Get the movie at the position
        Post post = posts.get(position);
        //Bind the movie data into the view holder
        try {
            holder.bind(post);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private static final int SECOND_MILLIS = 1000;
        private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

        private TextView tvHeader;
        private TextView tvTags;
        private TextView tvAuthor;
        private TextView tvCaption;
        private TextView tvDate;
        private ImageView ivProfilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
            tvTags = itemView.findViewById(R.id.tvTags);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
        }

        public void bind(Post post) throws com.parse.ParseException {
            Profile profile = (Profile) post.getUser().fetchIfNeeded().getParseObject("profile");
            tvHeader.setText(post.getHeader());
            tvTags.setText(post.getTags());
            tvAuthor.setText(profile.getFirstName()+" "+profile.getLastName());
            tvCaption.setText(post.getDescription());
            String date = post.getCreatedAt().toString();
            tvDate.setText(getRelativeTimeAgo(date));
            ParseFile image = profile.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivProfilePic);
            }
        }

        public String getRelativeTimeAgo(String rawJsonDate) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            long time = 0;

            try {
                time = sf.parse(rawJsonDate).getTime();
                long now = System.currentTimeMillis();

                final long diff = now - time;
                if (diff < MINUTE_MILLIS) {
                    return "just now";
                } else if (diff < 2 * MINUTE_MILLIS) {
                    return "a minute ago";
                } else if (diff < 50 * MINUTE_MILLIS) {
                    return diff / MINUTE_MILLIS + " minutes ago";
                } else if (diff < 90 * MINUTE_MILLIS) {
                    return "an hour ago";
                } else if (diff < 24 * HOUR_MILLIS) {
                    if (diff / HOUR_MILLIS == 1) {
                        return "an hour ago";
                    } else {
                        return diff / HOUR_MILLIS + " hours ago";
                    }
                } else if (diff < 48 * HOUR_MILLIS) {
                    return "yesterday";
                } else {
                    if (diff / DAY_MILLIS == 1) {
                        return "yesterday";
                    } else {
                        return diff / DAY_MILLIS + " days ago";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }

    }
}
