package biz.vumobile.videomate.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by toukirul on 10/1/2018.
 */

public class BroadcastDataPass extends BroadcastReceiver {

    static String state;

    @Override
    public void onReceive(Context context, Intent intent) {

        state = intent.getStringExtra("state");
    }

    public static String getSate(){
        return state;
    }
}