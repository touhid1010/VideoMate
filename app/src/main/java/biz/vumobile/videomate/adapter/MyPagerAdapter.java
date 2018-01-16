package biz.vumobile.videomate.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import biz.vumobile.videomate.view.fragment.FragmentFollow;
import biz.vumobile.videomate.view.fragment.FragmentLatest;
import biz.vumobile.videomate.view.fragment.FragmentPopular;

/**
 * Created by IT-10 on 1/14/2018.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private String fragments[] = {"Follow","Popular", "Latest"};

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentFollow();
            case 1:
                return new FragmentPopular();
            case 2:
                return new FragmentLatest();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
