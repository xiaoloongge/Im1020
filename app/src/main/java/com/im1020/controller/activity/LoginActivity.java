package com.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.im1020.MainActivity;
import com.im1020.R;
import com.im1020.modle.Modle;
import com.im1020.modle.bean.UserInfo;
import com.im1020.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_et_username)
    EditText loginEtUsername;
    @Bind(R.id.login_et_password)
    EditText loginEtPassword;
    @Bind(R.id.login_btn_register)
    Button loginBtnRegister;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;
    private String password;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.login_btn_register, R.id.login_btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_register:
               if (validate()){

                   Modle.getInstance().getGlobalThread().execute(new Runnable() {
                       @Override
                       public void run() {

                           //去服务器注册
                           try {
                               EMClient.getInstance()
                                       .createAccount(username,password);

                            ShowToast.showUI(LoginActivity.this,"注册成功");
                           } catch (HyphenateException e) {
                               e.printStackTrace();
                            ShowToast.showUI(LoginActivity.this,
                                    "注册失败"+e.getMessage());
                           }
                       }
                   });
               }
                break;
            case R.id.login_btn_login:
                if (validate()){
                    Modle.getInstance().getGlobalThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            EMClient.getInstance().login(username, password, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    //登录成功后需要的处理
                                Modle.getInstance().loginSuccess(EMClient.getInstance().getCurrentUser());
                                //将用户保存到数据库
                                Modle.getInstance().getAccountDao()
                                .addAccount(new UserInfo(EMClient.getInstance().getCurrentUser()));
                                //跳转
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                //结束
                                finish();
                            }

                                @Override
                                public void onError(int i, String s) {
                                    ShowToast.showUI(LoginActivity.this,
                                            "登录失败"+s);
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        }
                    });
                }

                break;
        }
    }

    /*
    * 验证 用户名和密码是否为空
    * */
    private boolean validate() {
        password = loginEtPassword.getText().toString().trim();
        username = loginEtUsername.getText().toString().trim();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)){
            ShowToast.show(this,"账号或密码为空");
            return false;
        }
        return true;
    }
}
