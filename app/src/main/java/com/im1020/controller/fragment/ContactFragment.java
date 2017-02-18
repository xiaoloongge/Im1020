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

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.im1020.R;
import com.im1020.controller.activity.InviteActivity;
import com.im1020.modle.Modle;
import com.im1020.modle.bean.UserInfo;
import com.im1020.utils.Constant;
import com.im1020.utils.ShowToast;
import com.im1020.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //初始化数据
        initData();
        //监听事件
        initListener();
    }

    private void initListener() {
        //联系人点击监听
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {

                //跳转
                Intent intent = new Intent(getActivity(),ChatActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();


    }

    private void initData() {

        //获取联系人
        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    //从服务器获取联系人
                    List<String> contacts =
                            EMClient.getInstance().contactManager().getAllContactsFromServer();

                    //保存数据库
                    //转化数据
                    List<UserInfo> userinfos = new ArrayList<UserInfo>();

                    for (int i = 0; i < contacts.size(); i++) {
                        userinfos.add(new UserInfo(contacts.get(i)));
                    }

                    Modle.getInstance().getDbManager().getContactDao()
                            .saveContacts(userinfos,true);
                    //内存和网页
                    refreshContact();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void refreshContact() {
        //从本地取数据
        List<UserInfo> contacts = Modle.getInstance().getDbManager().getContactDao()
                .getContacts();
        //校验
        if (contacts == null){
            return;
        }

        //转换数据
        Map<String,EaseUser> maps = new HashMap<>();
        for (UserInfo userInfo:contacts) {
            EaseUser user = new EaseUser(userInfo.getHxid());
            maps.put(userInfo.getHxid(),user);
        }
        setContactsMap(maps);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContact();
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
