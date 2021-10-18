package com.qeecan.multical.Account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qeecan.multical.Account.bean.TypeBean;
import com.qeecan.multical.R;

import java.util.List;


public class TypeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    List<TypeBean> typeBeanList;

    public TypeAdapter(Context context, List<TypeBean> typeBeanList) {
        layoutInflater = LayoutInflater.from(context);
        this.typeBeanList = typeBeanList;
    }

    @Override
    public int getCount() {
        return typeBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_account_gv, null);
            //查找布局当中的控件
            viewHolder.iv_icon = convertView.findViewById(R.id.item_account_edit_iv);
            viewHolder.tv_name = convertView.findViewById(R.id.item_account_edit_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TypeBean typeBean = typeBeanList.get(position);
        viewHolder.iv_icon.setImageResource(typeBean.getCimageId());
        viewHolder.tv_name.setText(typeBean.getTypename());

        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
    }
}

