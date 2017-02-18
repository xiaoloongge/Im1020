package com.im1020.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.im1020.controller.activity.ChatActivity;
import com.im1020.utils.ShowToast;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class ConversationFragment extends EaseConversationListFragment {


    @Override
    protected void initView() {
        super.initView();

        //清除原有的联系人，否则会出现重复的情况
        conversationList.clear();

        //会话item点击事件
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {

                //跳转
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                //传环信ID
                intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.getUserName());

                if (conversation.getType()
                        == EMConversation.EMConversationType.GroupChat){
                    //传入群聊天类型
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                }

                startActivity(intent);
            }
        });

        //注册一个监听
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                //接受到消息

                EaseUI.getInstance().getNotifier().onNewMesg(list);
                //刷新
                refresh();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }
}
