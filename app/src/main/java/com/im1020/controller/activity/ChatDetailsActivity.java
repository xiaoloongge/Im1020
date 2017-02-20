package com.im1020.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.GridView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.im1020.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatDetailsActivity extends AppCompatActivity {

    @Bind(R.id.gv_group_detail)
    GridView gvGroupDetail;
    @Bind(R.id.bt_group_detail)
    Button btGroupDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        ButterKnife.bind(this);


        initData();

    }

    private void initData() {

        //获取群id
        String groupid = getIntent().getStringExtra("groupid");

        if (TextUtils.isEmpty(groupid)){

            return;
        }
        //获取当前的群组
        EMGroup group = EMClient.getInstance().groupManager().getGroup(groupid);
        //获取群主
        String owner = group.getOwner();
        if (EMClient.getInstance().getCurrentUser().equals(owner)){
            //是群主
            btGroupDetail.setText("解散群");
        }else{
            //是成员
            btGroupDetail.setText("退群");
        }
    }

    @OnClick(R.id.bt_group_detail)
    public void onClick() {


    }
}
