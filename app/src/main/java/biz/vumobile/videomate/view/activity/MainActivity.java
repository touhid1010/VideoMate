package biz.vumobile.videomate.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import biz.vumobile.videomate.adapter.MyPagerAdapter;
import biz.vumobile.videomate.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private int current_position;

    ImageButton imageButtonRecord;
    Button buttonHome, buttonMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

        // tab layout
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonRecord:
                startActivity(new Intent(this, CameraActivity.class));
                break;

            case R.id.buttonHome:

                break;

            case R.id.buttonMe:

                break;
        }
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
}
