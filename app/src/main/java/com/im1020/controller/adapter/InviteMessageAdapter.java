package com.im1020.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.im1020.R;
import com.im1020.modle.bean.GroupInfo;
import com.im1020.modle.bean.InvitationInfo;
import com.im1020.modle.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/2/17.
 */

public class InviteMessageAdapter extends BaseAdapter {

    private Context context;

    private List<InvitationInfo> invitationInfos;

    public InviteMessageAdapter(Context context) {
        this.context = context;
        invitationInfos = new ArrayList<>();
    }

    public void refresh(List<InvitationInfo> invitationInfos) {

        //校验
        if (invitationInfos == null) {
            return;
        }
        this.invitationInfos.clear();
        //添加数据
        this.invitationInfos.addAll(invitationInfos);
        //刷新界面
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return invitationInfos == null ? 0 : invitationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return invitationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //创建viewHolder
        ViewHolder viewHolder = null;

        if (convertView == null) {
            //创建convertView
            convertView = View.inflate(context, R.layout.adapter_invite_message_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //绑定数据
        InvitationInfo invitationInfo = invitationInfos.get(position);

        GroupInfo groupInfo = invitationInfo.getGroupInfo();

        if (groupInfo != null){
            //群邀请

        }else{
            //联系人邀请
            UserInfo userInfo = invitationInfo.getUserInfo();
            viewHolder.tvInviteName.setText(userInfo.getUsername());
            //隐藏button
            viewHolder.btInviteAccept.setVisibility(View.GONE);
            viewHolder.btInviteReject.setVisibility(View.GONE);

            //新邀请
            if (invitationInfo.getStatus()
                    == InvitationInfo.InvitationStatus.NEW_INVITE){

                //展示button
                viewHolder.btInviteReject.setVisibility(View.VISIBLE);
                viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
                //设置reason
                if (invitationInfo.getReason() == null){
                    viewHolder.tvInviteReason.setText("邀请好友");
                }else{
                    viewHolder.tvInviteReason.setText(invitationInfo.getReason());
                }
            }else if(invitationInfo.getStatus() //邀请被接受
                    == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER){
                if (invitationInfo.getReason() == null){
                    viewHolder.tvInviteReason.setText("邀请被接受");
                }else{
                    viewHolder.tvInviteReason.setText(invitationInfo.getReason());
                }
            }else if(invitationInfo.getStatus()  //接受邀请
                    == InvitationInfo.InvitationStatus.INVITE_ACCEPT){
                if (invitationInfo.getReason() == null){
                    viewHolder.tvInviteReason.setText("接受邀请");
                }else{
                    viewHolder.tvInviteReason.setText(invitationInfo.getReason());
                }
            }
        }

        //返回布局
        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.tv_invite_name)
        TextView tvInviteName;
        @Bind(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @Bind(R.id.bt_invite_accept)
        Button btInviteAccept;
        @Bind(R.id.bt_invite_reject)
        Button btInviteReject;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
