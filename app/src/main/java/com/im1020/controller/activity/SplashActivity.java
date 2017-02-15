package com.im1020.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.im1020.R;
import com.im1020.modle.Modle;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==0){
                //进入主界面或登录界面
                enterMainOrLogin();
            }
        }
    };

    private void enterMainOrLogin() {

        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                //去环信服务器获取是否登录
                boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();

                if (loggedInBefore){

                    //登录成功后需要的处理
                    Modle.getInstance().loginSuccess(EMClient.getInstance().getCurrentUser());
                    //登录过
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    //结束当前页面
                    finish();
                }else{
                    //没有登录

                    //跳转到登录页面
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //结束当前页面
                    finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //发送延迟消息
        handler.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除callbacks和messages
        handler.removeCallbacksAndMessages(null);
    }
}
