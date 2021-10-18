package com.qeecan.multical.Schedule.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.qeecan.multical.R;
import com.qeecan.multical.Schedule.bean.ScheduleBean;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends BaseAdapter implements Filterable {
    private Context context;
    List<ScheduleBean> sDatas;
    List<ScheduleBean> backschedule;
    private SFilter mFilter;

    public ScheduleAdapter(Context context,List<ScheduleBean> sDatas) {
        this.context=context;
        this.sDatas = sDatas;
        backschedule = sDatas;
    }

    @Override
    public int getCount() {
        return sDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return sDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View v = View.inflate(context, R.layout.item_schedule_lv, null);
        TextView tv_content = (TextView)v.findViewById(R.id.item_alarm_tv_content);
        TextView tv_time = (TextView)v.findViewById(R.id.item_alarm_tv_time);

        tv_content.setText(sDatas.get(position).getContent());
        tv_time.setText(sDatas.get(position).getClock());

        v.setTag(sDatas.get(position).getId());
        return v;

    }
    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new ScheduleAdapter.SFilter();
        }
        return mFilter;
    }
    class SFilter extends Filter {
        //用performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<ScheduleBean> slist;
            if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                slist = backschedule;
            } else {//否则把符合条件的数据对象添加到集合中
                slist = new ArrayList<>();
                for (ScheduleBean schedule : backschedule) {
                    if (schedule.getContent().contains(charSequence)) {
                        slist.add(schedule);
                    }
                }
            }
            result.values = slist; //将得到的集合保存到FilterResults的value变量中
            result.count = slist.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            sDatas = (List<ScheduleBean>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }


    }

}
