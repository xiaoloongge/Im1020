package com.im1020;

import android.app.Application;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.im1020.modle.Modle;

/**
 * Created by Administrator on 2017/2/14.
 */

public class ImApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化环信SDK
        initHXSdk();

        //初始化Modle
        Modle.getInstance().init(this);
    }

    private void initHXSdk() {
        //配置文件
        EMOptions options = new EMOptions();
        //总是接受邀请
        options.setAcceptInvitationAlways(false);
        //自动接受群邀请
        options.setAutoAcceptGroupInvitation(false);
        //初始化SDK
        EaseUI.getInstance().init(this,options);
    }
}
