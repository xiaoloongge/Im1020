package com.im1020.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.im1020.R;
import com.im1020.controller.fragment.ContactFragment;
import com.im1020.controller.fragment.ConversationFragment;
import com.im1020.controller.fragment.SettingsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_fl)
    FrameLayout mainFl;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    private Fragment settingsFragment;
    private Fragment conversationFragment;
    private Fragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initData();
        initListener();
    }


    private void initData() {
        //创建fragment
        settingsFragment = new SettingsFragment();
        conversationFragment = new ConversationFragment();
        contactFragment = new ContactFragment();
        switchFragment(R.id.rb_main_conversation);
    }

    private void initListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //切换fragment
              switchFragment(checkedId);
            }
        });

    }

    private void switchFragment(int checkedId) {
        Fragment fragment = null;
        switch (checkedId) {
            case R.id.rb_main_contact:
                fragment = contactFragment;
                break;
            case R.id.rb_main_conversation:
                fragment = conversationFragment;
                break;
            case R.id.rb_main_setting:
                fragment = settingsFragment;
                break;
        }
        if (fragment==null){
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fl,fragment).commit();
    }
}
