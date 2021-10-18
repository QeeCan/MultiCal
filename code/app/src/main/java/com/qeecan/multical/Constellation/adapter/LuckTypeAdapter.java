package com.qeecan.multical.Constellation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qeecan.multical.Constellation.bean.LuckTypeBean;
import com.qeecan.multical.R;

import java.util.List;

public class LuckTypeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    List<LuckTypeBean> mDatas;


    public LuckTypeAdapter(Context context, List<LuckTypeBean> luckTypeBeanList) {
        this.mDatas = luckTypeBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {

        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new LuckTypeAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_luck_lv, null);

            viewHolder.tv_title = convertView.findViewById(R.id.item_luck_tv_title);
            viewHolder.tv_content = convertView.findViewById(R.id.item_luck_tv_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LuckTypeBean luckTypeBean = mDatas.get(position);
        viewHolder.tv_title.setText(luckTypeBean.getCtitle());
        viewHolder.tv_content.setText(luckTypeBean.getCcontent());
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_content;
    }

}

