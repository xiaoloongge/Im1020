package com.im1020.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/14.
 */

public class ShowToast {


    public static void show(Activity activity,String context){
        Toast.makeText(activity, context, Toast.LENGTH_SHORT).show();
    }

    public static void showUI(final Activity activity, final String context){

        //在分线程中activity有可能为空所以需要判断
        if (activity == null){
            return;
        }
        //在主线程中执行
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(activity,context);
            }
        });
    }


}
