package com.im1020.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.im1020.R;
import com.im1020.controller.activity.InviteActivity;
import com.im1020.utils.Constant;
import com.im1020.utils.ShowToast;
import com.im1020.utils.SpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 */
public class ContactFragment extends EaseContactListFragment {


    @Bind(R.id.contanct_iv_invite)
    ImageView contanctIvInvite;
    @Bind(R.id.ll_new_friends)
    LinearLayout llNewFriends;
    @Bind(R.id.ll_groups)
    LinearLayout llGroups;
    private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isShow();
        }
    };
    private LocalBroadcastManager manager;

    @Override
    protected void initView() {
        super.initView();

        //初始化头布局
        View view = View.inflate(getActivity(), R.layout.fragment_contact_head, null);
        ButterKnife.bind(this,view);
        //添加头布局
        listView.addHeaderView(view);
        //添加actionbar右侧的加号
        titleBar.setRightImageResource(R.mipmap.em_add);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到邀请界面
                Intent intent = new Intent(getActivity(),InviteActivity.class);
                startActivity(intent);
            }
        });
        //初始化小红点
        isShow();

        //注册广播
        manager = LocalBroadcastManager.getInstance(getActivity());

        manager.registerReceiver(recevier,new IntentFilter(Constant.NEW_INVITE_CHANGE));
    }


    @Override
    protected void setUpView() {
        super.setUpView();

    }

    @OnClick({R.id.ll_new_friends, R.id.ll_groups})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_new_friends:
               //隐藏小红点
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,false);
                isShow();
                //跳转
                Intent intent = new Intent(getActivity(),InviteMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_groups:
                ShowToast.show(getActivity(), "bbb");
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        manager.unregisterReceiver(recevier);
    }

    public void isShow() {
        boolean isShow = SpUtils.getInstace()
                .getBoolean(SpUtils.NEW_INVITE, false);
        contanctIvInvite.setVisibility(isShow? View.VISIBLE : View.GONE);
    }
}
