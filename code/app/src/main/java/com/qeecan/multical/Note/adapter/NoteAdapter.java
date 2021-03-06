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
        //???performFiltering(CharSequence charSequence)?????????????????????????????????
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<NoteBean> nlist;
            if (TextUtils.isEmpty(charSequence)) {//?????????????????????????????????????????????????????????????????????
                nlist = backnote;
            } else {//??????????????????????????????????????????????????????
                nlist = new ArrayList<>();
                for (NoteBean note : backnote) {
                    if (note.getTitle().contains(charSequence) || note.getContent().contains(charSequence)) {
                        nlist.add(note);
                    }

                }
            }
            result.values = nlist; //???????????????????????????FilterResults???value?????????
            result.count = nlist.size();//???????????????????????????FilterResults???count?????????

            return result;
        }
        //???publishResults????????????????????????????????????
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            nDatas = (List<NoteBean>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//???????????????????????????
            }else {
                notifyDataSetInvalidated();//??????????????????
            }
        }


    }

}