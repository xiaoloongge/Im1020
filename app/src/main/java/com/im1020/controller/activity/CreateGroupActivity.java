package com.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.im1020.R;
import com.im1020.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGroupActivity extends AppCompatActivity {

    @Bind(R.id.et_newgroup_name)
    EditText etNewgroupName;
    @Bind(R.id.et_newgroup_desc)
    EditText etNewgroupDesc;
    @Bind(R.id.cb_newgroup_public)
    CheckBox cbNewgroupPublic;
    @Bind(R.id.cb_newgroup_invite)
    CheckBox cbNewgroupInvite;
    @Bind(R.id.bt_newgroup_create)
    Button btNewgroupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.bt_newgroup_create)
    public void onClick() {

        if (validate()){

            //跳转
            Intent intent = new Intent(CreateGroupActivity.this,PickContactActivity.class);
            startActivity(intent);
        }
    }

    private boolean validate() {

        String desc = etNewgroupDesc.getText().toString().trim();
        String groupname = etNewgroupName.getText().toString().trim();

        if (TextUtils.isEmpty(groupname)){
            ShowToast.show(this,"群名称不能为空");
            return false;
        }
        if (TextUtils.isEmpty(desc)){
            ShowToast.show(this,"群简介不能为空");
            return false;
        }
        return true;
    }
}
