package com.im1020.controller.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.im1020.R;
import com.im1020.controller.adapter.InviteMessageAdapter;
import com.im1020.modle.Modle;
import com.im1020.modle.bean.InvitationInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InviteMessageActivity extends AppCompatActivity {

    @Bind(R.id.invite_msg_lv)
    ListView inviteMsgLv;
    private InviteMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_message);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {

        adapter = new InviteMessageAdapter(this);

        inviteMsgLv.setAdapter(adapter);

        refresh();
    }


    private void refresh() {

        //获取数据
        List<InvitationInfo> invitations = Modle.getInstance().getDbManager().getInvitationDao()
                .getInvitations();
        //刷新数据
        if (invitations == null){
            return;
        }
        adapter.refresh(invitations);
    }
}
