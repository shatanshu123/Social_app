package com.shatanshu.social_app.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.shatanshu.social_app.R;
import com.shatanshu.social_app.Utils.ViewCommentsFragment;
import com.shatanshu.social_app.Utils.ViewPostFragment;
import com.shatanshu.social_app.Utils.ViewProfileFragment;
import com.shatanshu.social_app.models.Photo;
import com.shatanshu.social_app.models.User;

import static com.shatanshu.social_app.R.*;

public class ProfileActivity extends AppCompatActivity
        implements ProfileFragment.OnGridImageSelectedListener,
        ViewPostFragment.OnCommentThreadSelectedListener,
        ViewProfileFragment.OnGridImageSelectedListener {
    private static final String TAG = "ProfileActivity";
    @Override
    public void onCommentThreadSelectedListener(Photo photo) {
        Log.d(TAG, "onCommentThreadSelectedListener: selected a comment thread");
        ViewCommentsFragment fragment = new ViewCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(string.photo), photo);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id.container, fragment);
        transaction.addToBackStack(getString(string.view_comments_fragment));
        transaction.commit();

    }
    @Override
    public void onGridImageSelected(Photo photo, int activityNumber) {
        Log.d(TAG, "onGridImageSelected: selected an image gridview: " + photo.toString());
        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(string.photo), photo);
        args.putInt(getString(string.activity_number), activityNumber);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id.container, fragment);
        transaction.addToBackStack(getString(string.view_post_fragment));
        transaction.commit();
    }

    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;
    private Context mContext = ProfileActivity.this;
    private ProgressBar mProgressBar;
    private ImageView profilePhoto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_profile);
        Log.d(TAG, "onCreate: started.");
        init();

    }

    private void init(){
        Log.d(TAG, "init: inflating " + getString(string.profile_fragment));
        Intent intent = getIntent();
        if(intent.hasExtra(getString(string.calling_activity))){
            Log.d(TAG, "init: searching for user object attached as intent extra");
            if(intent.hasExtra(getString(string.intent_user))) {
                User user = intent.getParcelableExtra(getString(string.intent_user));
                if(!user.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    Log.d(TAG, "init: inflating view Profile");


                    ViewProfileFragment fragment = new ViewProfileFragment();
                    Bundle args = new Bundle();
                    args.putParcelable(getString(string.intent_user),
                            intent.getParcelableExtra(getString(string.intent_user)));
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(id.container, fragment);
                    transaction.addToBackStack(getString(string.view_profile_fragment));
                    transaction.commit();

                }else {
                    Log.d(TAG, "init: inflating Profile");


                    ProfileFragment fragment = new ProfileFragment();
                    FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(id.container, fragment);

                    transaction.addToBackStack(getString(string.profile_fragment));
                    transaction.commit();
                }

            }else{
                Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }else {
            Log.d(TAG, "init: inflating Profile");


            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.replace(id.container, fragment);

            transaction.addToBackStack(getString(string.profile_fragment));
            transaction.commit();
        }
    }



}