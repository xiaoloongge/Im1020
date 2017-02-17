package com.im1020.modle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.im1020.modle.bean.InvitationInfo;
import com.im1020.modle.bean.UserInfo;
import com.im1020.utils.Constant;
import com.im1020.utils.SpUtils;

/**
 * Created by Administrator on 2017/2/15.
 */

public class GlobalListener {

    private final LocalBroadcastManager manager;

    public GlobalListener(Context context){
//        注册联系人监听
        EMClient.getInstance().contactManager().setContactListener(listener);
        //本地广播
        manager = LocalBroadcastManager.getInstance(context);
    }

    EMContactListener listener = new EMContactListener() {

        //收到好友邀请  别人加你
        @Override
        public void onContactInvited(String username, String reason) {

            //加到邀请信息表
            InvitationInfo invitation = new InvitationInfo();
            invitation.setUserInfo(new UserInfo(username));
            invitation.setReason(reason);
            invitation.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);

            Modle.getInstance().getDbManager().getInvitationDao()
                    .addInvitation(invitation);

            //保存小红点的状态
            SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));
        }

        //好友请求被同意  你加别人的时候 别人同意了
        @Override
        public void onContactAgreed(String username) {

            //添加到邀请信息表
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setUserInfo(new UserInfo(username));
            invitationInfo.setReason("邀请被接受");
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
            Modle.getInstance().getDbManager().getInvitationDao()
                    .addInvitation(invitationInfo);

            //保存小红点的状态
            SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));

        }

        //被删除时回调此方法
        @Override
        public void onContactDeleted(String username) {

            //删除邀请信息
            Modle.getInstance().getDbManager().getInvitationDao()
                    .removeInvitation(username);
            //删除联系人
            Modle.getInstance().getDbManager().getContactDao()
                    .deleteContactByHxId(username);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }


        //增加了联系人时回调此方法  当你同意添加好友
        @Override
        public void onContactAdded(String username) {

            //保存联系人
            Modle.getInstance().getDbManager().getContactDao()
                    .saveContact(new UserInfo(username),true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }

        //好友请求被拒绝  你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {

            //保存小红点的状态
            SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));
        }
    };
}
