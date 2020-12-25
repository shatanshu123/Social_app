package com.shatanshu.social_app.Share;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.shatanshu.social_app.R;
import com.shatanshu.social_app.Utils.BottomNavigationViewHelper;
import com.shatanshu.social_app.Utils.Permissions;
import com.shatanshu.social_app.Utils.SectionPagerAdapter;
import com.shatanshu.social_app.Utils.SectionStatePagerAdapter;

public class ShareActivity extends AppCompatActivity {
    private static final String TAG = "ShareActivity";
    private static final int ACTIVITY_NUM = 2;
    private static final int Verify_PERMISSIONS_REQUEST = 1;
    private ViewPager mViewPager;
    private Context mContext = ShareActivity.this;

    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Log.d(TAG, "onCreate: started.");
        if(checkPermissionsArray(Permissions.PERMISSIONS)){
            setupViewPager();

        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }
     //   setupBottomNavigationView();
    }
    public int getCurrentTabNumber(){
        return mViewPager.getCurrentItem();
    }
    private void setupViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment());
        adapter.addFragment(new PhotoFragment());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));
    }
    public int getTask(){
        Log.d(TAG, "getTask: TASK: " + getIntent().getFlags());
        return getIntent().getFlags();
    }
    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");
        ActivityCompat.requestPermissions(
                ShareActivity.this,
                permissions,
                Verify_PERMISSIONS_REQUEST
        );
    }
    public boolean checkPermissionsArray(String[] permission){
        Log.d(TAG, "checkPermissionArray: checking permissions array.");
        for(int i=0;i<permission.length;i++){
            String check = permission[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }
    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermission: " + permission);
        int permissionRequest = ActivityCompat.checkSelfPermission(ShareActivity.this, permission);
        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermission: \n Permission was granted for: " + permission);

            return true;
        }
    }
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
