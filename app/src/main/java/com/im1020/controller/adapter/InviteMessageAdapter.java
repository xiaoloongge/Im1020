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

    public InviteMessageAdapter(Context context,OnInviteChangeListener onInviteChangeListener) {
        this.context = context;
        invitationInfos = new ArrayList<>();
        this.onInviteChangeListener = onInviteChangeListener;
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
        final InvitationInfo invitationInfo = invitationInfos.get(position);

        GroupInfo groupInfo = invitationInfo.getGroupInfo();

        if (groupInfo != null){
            //群邀请

            viewHolder.tvInviteName.setText(groupInfo.getInvitePerson());

            //隐藏按钮
            viewHolder.btInviteReject.setVisibility(View.GONE);
            viewHolder.btInviteAccept.setVisibility(View.GONE);
            switch(invitationInfo.getStatus()){
                // 您的群申请请已经被接受
                case GROUP_APPLICATION_ACCEPTED:

                    viewHolder.tvInviteReason.setText("您的群申请请已经被接受");
                    break;
                //  您的群邀请已经被接收
                case GROUP_INVITE_ACCEPTED:
                    viewHolder.tvInviteReason.setText("您的群邀请已经被接收");
                    break;

                // 你的群申请已经被拒绝
                case GROUP_APPLICATION_DECLINED:
                    viewHolder.tvInviteReason.setText("你的群申请已经被拒绝");
                    break;

                // 您的群邀请已经被拒绝
                case GROUP_INVITE_DECLINED:
                    viewHolder.tvInviteReason.setText("您的群邀请已经被拒绝");
                    break;

                // 您收到了群邀请
                case NEW_GROUP_INVITE:
                    viewHolder.tvInviteReason.setText("您收到了群邀请");
                    //展示按钮
                    viewHolder.btInviteReject.setVisibility(View.VISIBLE);
                    viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
                    viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onInviteChangeListener != null){
                                onInviteChangeListener.onInviteAccept(invitationInfo);
                            }
                        }
                    });
                    viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onInviteChangeListener != null){
                                onInviteChangeListener.onInviteReject(invitationInfo);
                            }
                        }
                    });
                    break;

                // 您收到了群申请
                case NEW_GROUP_APPLICATION:
                    viewHolder.tvInviteReason.setText("您收到了群申请");
                    viewHolder.btInviteReject.setVisibility(View.VISIBLE);
                    viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
                    viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onInviteChangeListener != null){
                                onInviteChangeListener.onApplicationAccept(invitationInfo);
                            }
                        }
                    });
                    viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onInviteChangeListener != null){
                                onInviteChangeListener.onApplicationReject(invitationInfo);
                            }
                        }
                    });
                    break;

                // 你接受了群邀请
                case GROUP_ACCEPT_INVITE:
                    viewHolder.tvInviteReason.setText("你接受了群邀请");
                    break;

                // 您批准了群申请
                case GROUP_ACCEPT_APPLICATION:
                    viewHolder.tvInviteReason.setText("您批准了群申请");
                    break;

                // 你拒绝了群邀请
                case GROUP_REJECT_INVITE:
                    viewHolder.tvInviteReason.setText("你拒绝了群邀请");
                    break;

                // 您拒绝了群申请
                case GROUP_REJECT_APPLICATION:
                    viewHolder.tvInviteReason.setText("您拒绝了群申请");
                    break;
            }

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
                //接受按钮的监听
                viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onInviteChangeListener != null){
                            onInviteChangeListener.onAccept(invitationInfo);
                        }
                    }
                });
                //拒绝按钮的监听
                viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onInviteChangeListener != null){
                            onInviteChangeListener.onReject(invitationInfo);
                        }
                    }
                });
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

    /*
    * 第一步  定义接口
    * 第二步  定义接口的变量
    * 第三步  设置set方法
    * 第四步  接受接口的实例对象
    * 第五步  调用接口方法
    *
    * */
    private OnInviteChangeListener onInviteChangeListener;

    public void setOnInviteChangeListener(OnInviteChangeListener onInviteChangeListener) {
        this.onInviteChangeListener = onInviteChangeListener;
    }

    public interface OnInviteChangeListener{

        void onAccept(InvitationInfo info); //同意
        void onReject(InvitationInfo info);  //拒绝

        void onInviteAccept(InvitationInfo info);
        void onInviteReject(InvitationInfo info);

        void onApplicationAccept(InvitationInfo info);
        void onApplicationReject(InvitationInfo info);
    }
}
