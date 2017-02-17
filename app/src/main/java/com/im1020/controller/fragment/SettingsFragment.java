package com.im1020.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.im1020.R;
import com.im1020.controller.activity.LoginActivity;
import com.im1020.modle.Modle;
import com.im1020.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/15.
 */
public class SettingsFragment extends Fragment {

    @Bind(R.id.setting_btn_exit)
    Button settingBtnExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settingBtnExit.setText("退出登录（"+ EMClient.getInstance().getCurrentUser()+")");
    }

    @OnClick(R.id.setting_btn_exit)
    public void onClick() {


        /*
        * 第一步网络
        * 第二步本地
        * 第三步界面
        *
        * */
        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                //网络 告诉环信服务器 哥们要退出了
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //退出后其它操作
                        Modle.getInstance().exitLogin();
                        //跳转到登录界面
                        ShowToast.showUI(getActivity(),"退出成功");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        //结束当前页面
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, String s) {

                        ShowToast.showUI(getActivity(),"退出失败"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
