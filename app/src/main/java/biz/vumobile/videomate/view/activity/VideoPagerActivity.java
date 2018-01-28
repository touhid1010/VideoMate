package biz.vumobile.videomate.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import biz.vumobile.videomate.R;
import biz.vumobile.videomate.adapter.MyVerticalViewPagerAdapter;
import biz.vumobile.videomate.utils.VerticalViewPager;

public class VideoPagerActivity extends AppCompatActivity {

    public static List<String> list = new ArrayList<>();
    private VerticalViewPager viewPager;
    private MyVerticalViewPagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_pager);

        viewPager = findViewById(R.id.verticalViewPager);

//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521346120032467a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521368630541475a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521305112386451a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521302776083623a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521368630541475a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521346120032467a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521305112386451a.mp4");
//        list.add("http://202.164.213.242/VUMate/graphics/videos/636521302776083623a.mp4");

        fragmentManager = getSupportFragmentManager();

        viewPager = findViewById(R.id.verticalViewPager);
        viewPager.setOffscreenPageLimit(3);
        pagerAdapter = new MyVerticalViewPagerAdapter(fragmentManager, list);
        viewPager.setAdapter(pagerAdapter);




    }
}
