package com.im1020.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.im1020.R;
import com.im1020.modle.bean.PickInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/20.
 */

public class PickAdapter extends BaseAdapter {

    private Context context;
    private List<PickInfo> pickInfos;
    private List<String> members;

    public PickAdapter(Context context) {
        this.context = context;
        pickInfos = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void refresh(List<PickInfo> pickInfos,List<String> members) {

        if (pickInfos != null) {
            this.pickInfos.clear();
            this.pickInfos.addAll(pickInfos);
            notifyDataSetChanged();
        }

        if (members != null && members.size()>=0){

            this.members.clear();
            this.members.addAll(members);
        }

      /*  if (members != null && members.size()==0){

        }*/

    }

    @Override
    public int getCount() {
        return pickInfos == null ? 0 : pickInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return pickInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            convertView = View.inflate(context, R.layout.adapter_pick_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PickInfo pickInfo = pickInfos.get(position);

        viewHolder.cbItemPickContacts.setChecked(pickInfo.isCheck());

        viewHolder.tvItemPickContactsName.setText(pickInfo.getUserInfo().getUsername());

        /*
        * 根据members可以删除出来 已经添加过的好友
        *
        * */

        return convertView;
    }


    /*
    * 获取被选择中的联系人
    * */
    public List<String> getContactCheck() {

        //校验
        if (pickInfos == null){
            return null;
        }

        List<String> userInfos = new ArrayList<>();
        for (PickInfo pickInfo:pickInfos
             ) {
            //判断是否选中了联系人
            if (pickInfo.isCheck()){
                userInfos.add(pickInfo.getUserInfo().getHxid());
            }
        }

        return userInfos;
    }


    class ViewHolder {

        @Bind(R.id.cb_item_pick_contacts)
        CheckBox cbItemPickContacts;
        @Bind(R.id.tv_item_pick_contacts_name)
        TextView tvItemPickContactsName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
