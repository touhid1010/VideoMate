package biz.vumobile.videomate.view.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.MyPagerAdapter;
import biz.vumobile.videomate.login.MyLoginOperation;
import biz.vumobile.videomate.networking.ApiInterface;
import biz.vumobile.videomate.networking.RetrofitClient;
import biz.vumobile.videomate.utils.CallBackLatestData;
import biz.vumobile.videomate.utils.MyConstraints;
import biz.vumobile.videomate.view.fragment.FragmentLatest;
import biz.vumobile.videomate.view.fragment.FragmentMe;

import static biz.vumobile.videomate.utils.MyConstraints.FRAGMENT_TAG_ME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private int current_position;

    ImageButton imageButtonRecord;
    Button buttonHome, buttonMe;
    FragmentMe fragmentMe;
    FragmentManager fragmentManager;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

        // Tab layout
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        apiInterface = RetrofitClient.getRetrofitClient(MyConstraints.API_BASE).create(ApiInterface.class);

        runTimePermission();

    }

    private void initUI() {
        viewPager = findViewById(R.id.viewPager);
        imageButtonRecord = findViewById(R.id.imageButtonRecord);
        buttonHome = findViewById(R.id.buttonHome);
        buttonMe = findViewById(R.id.buttonMe);
        imageButtonRecord.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
        buttonMe.setOnClickListener(this);

        fragmentMe = new FragmentMe();
        fragmentManager = getSupportFragmentManager();

        registerReceiver(receiver, new IntentFilter("upload.success"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonRecord:
                startActivity(new Intent(this, CameraActivity.class));
                break;

            case R.id.buttonHome:
                removeFragment(fragmentMe);
                viewPager.setCurrentItem(1);
                break;

            case R.id.buttonMe:
                addFragment(R.id.frameLayoutFragmentPlaceholder, fragmentMe, FRAGMENT_TAG_ME);
                break;
        }
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String fragmentTag) {

        Fragment fragmentA = fragmentManager.findFragmentByTag(FRAGMENT_TAG_ME);
        if (fragmentA == null) {
            fragmentManager
                    .beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top)
                    .add(containerViewId, fragment, fragmentTag)
                    .disallowAddToBackStack()
                    .commit();
        } else {
            removeFragment(fragmentMe);
        }

    }



    protected void removeFragment(@NonNull Fragment fragment) {
        fragmentManager
                .beginTransaction().setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top)
                .remove(fragment)
                .commit();
    }

    private void runTimePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Please GRANT permissions to use this app!");
                builder.setTitle("Permission Alert!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        runTimePermission();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.create().show();
            }
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        current_position = position;
        Log.d("CurrentPosition", String.valueOf(current_position));
    }

    @Override
    public void onPageSelected(int position) {
        current_position = position;
        Log.d("CurrentPosition", String.valueOf(current_position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    CallBackLatestData callBackLatestData;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("upload.success")) {
                viewPager.setCurrentItem(0);
                viewPager.setCurrentItem(2);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "resume");

        // Reload user data after fb login
        if (!MyLoginOperation.getInstance(this).getUserId().equals("")) {
            MyLoginOperation.getInstance(this).getUserDataForSingleton(MyLoginOperation.getInstance(this).getUserId());
        }

        // Reload video grid data after upload video


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}
