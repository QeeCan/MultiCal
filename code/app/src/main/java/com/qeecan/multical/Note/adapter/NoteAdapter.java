package com.qeecan.multical.Note.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.qeecan.multical.Note.bean.NoteBean;
import com.qeecan.multical.R;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<NoteBean> nDatas;
    private List<NoteBean> backnote;
    private NFilter mFilter;

    public NoteAdapter(Context context, List<NoteBean> nDatas) {
        this.context = context;
        this.nDatas = nDatas;
        backnote = nDatas;
    }


    @Override
    public int getCount() {
        return nDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return nDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = View.inflate(context, R.layout.item_note_lv, null);
        TextView tv_title = (TextView) view.findViewById(R.id.item_note_tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.item_note_tv_content);
        TextView tv_tag = (TextView) view.findViewById(R.id.item_note_tv_tag);
        TextView tv_time = (TextView) view.findViewById(R.id.item_note_tv_time);

        tv_title.setText(nDatas.get(position).getTitle());
        tv_content.setText(nDatas.get(position).getContent());
        tv_tag.setText(nDatas.get(position).getTag());
        tv_time.setText(nDatas.get(position).getTime());

        view.setTag(nDatas.get(position).getId());

        return view;
    }

    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new NFilter();
        }
        return mFilter;
    }

    class NFilter extends Filter {
        //在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<NoteBean> nlist;
            if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                nlist = backnote;
            } else {//否则把符合条件的数据对象添加到集合中
                nlist = new ArrayList<>();
                for (NoteBean note : backnote) {
                    if (note.getTitle().contains(charSequence) || note.getContent().contains(charSequence)) {
                        nlist.add(note);
                    }

                }
            }
            result.values = nlist; //将得到的集合保存到FilterResults的value变量中
            result.count = nlist.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            nDatas = (List<NoteBean>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }


    }

}