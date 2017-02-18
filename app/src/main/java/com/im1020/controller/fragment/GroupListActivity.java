package com.im1020.controller.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.im1020.R;
import com.im1020.controller.adapter.GroupListAdapter;
import com.im1020.modle.Modle;
import com.im1020.utils.ShowToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupListActivity extends AppCompatActivity {

    @Bind(R.id.lv_grouplist)
    ListView lvGrouplist;
    private GroupListAdapter adapter;
    private LinearLayout groupListHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        ButterKnife.bind(this);

        initView();

        initData();

        initListener();
    }

    private void initData() {

        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    //从网络获取群组信息
                    List<EMGroup> groups = EMClient.getInstance().groupManager()
                            .getJoinedGroupsFromServer();

                    //内存和页面
                    refresh();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refresh() {

        List<EMGroup> allGroups = EMClient.getInstance().groupManager().getAllGroups();

        if (allGroups == null){
            return;
        }

        adapter.refresh(allGroups);
    }

    private void initView() {

        //添加头布局
        View headView = View.inflate(this,R.layout.group_list_head,null);

        groupListHead = (LinearLayout) headView.findViewById(R.id.ll_grouplist);

        lvGrouplist.addHeaderView(headView);

        //设置适配器
        adapter = new GroupListAdapter(this);

        lvGrouplist.setAdapter(adapter);
    }


    private void initListener() {

        groupListHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.show(GroupListActivity.this,"阿福老师附体了");
            }
        });
    }

}
