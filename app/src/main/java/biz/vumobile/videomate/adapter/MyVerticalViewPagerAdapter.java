package biz.vumobile.videomate.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import biz.vumobile.videomate.view.fragment.FragmentLatest;

/**
 * Created by IT-10 on 1/22/2018.
 */

public class MyVerticalViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> fragments;
    private Fragment fragment;

    public MyVerticalViewPagerAdapter(FragmentManager fm, List<String> l) {
        super(fm);
        this.fragments = l;
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new FragmentLatest();
        Bundle bundle = new Bundle();
        bundle.putString("key", fragments.get(position));
        fragment.setArguments(bundle);
        Log.d("FragmentListe", String.valueOf(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}