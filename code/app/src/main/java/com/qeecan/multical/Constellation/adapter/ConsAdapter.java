package com.qeecan.multical.Constellation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.qeecan.multical.Constellation.bean.ConsBean;
import com.qeecan.multical.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConsAdapter extends BaseAdapter {
   private LayoutInflater layoutInflater;

    List<ConsBean> consBeanList;
//    Context context;
//    public ConsAdapter(Context context, List<ConsBean> consBeanList) {
//        this.context = context;
//        this.consBeanList = consBeanList;
//    }



    public ConsAdapter(Context context,List<ConsBean> consBeanList) {
        this.consBeanList = consBeanList;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return consBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return consBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null) {
            viewHolder =new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_cons_gv, null);

            viewHolder.iv_icon= convertView.findViewById(R.id.item_cons_iv);
            viewHolder .tv_name= convertView.findViewById(R.id.item_cons_tv);

           convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ConsBean consBean = consBeanList.get(position);
        viewHolder.iv_icon.setImageResource(consBean.getConsimageId());
        viewHolder .tv_name.setText(consBean.getConsname());
        return convertView;
    }
        class ViewHolder {
            CircleImageView iv_icon;
            TextView tv_name;
        }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_cons_gv,null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//        }
////        获取数据源
//        ConsBean consBean = consBeanList.get(position);
//        holder.starIv.setImageResource(consBean.getConsimageId());
//        holder .starTv.setText(consBean.getConsname());
//
//        return convertView;
//    }
//
//    class ViewHolder{
//        CircleImageView starIv;
//        TextView starTv;
//        public ViewHolder(View view){
//            starIv = view.findViewById(R.id.item_cons_iv);
//            starTv = view.findViewById(R.id.item_cons_tv);
//        }
//    }
}
