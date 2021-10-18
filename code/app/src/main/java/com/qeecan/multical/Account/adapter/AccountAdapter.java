package com.qeecan.multical.Account.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.qeecan.multical.Account.bean.AccountBean;
import com.qeecan.multical.R;


import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<AccountBean> aDatas;
    private List<AccountBean> backaccount;
    private AFilter mFilter;

    public AccountAdapter(Context context, List<AccountBean> aDatas) {
        this.context = context;
        this.aDatas = aDatas;
        backaccount = aDatas;
    }

    @Override
    public int getCount() {
        return aDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return aDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = View.inflate(context, R.layout.item_account_lv, null);
        TextView tv_name = view.findViewById(R.id.item_account_tv_typename);
        TextView tv_rm = view.findViewById(R.id.item_account_tv_remark);
        TextView tv_time = view.findViewById(R.id.item_account_tv_time);
        TextView tv_yuan = view.findViewById(R.id.item_account_tv_money);


        tv_name.setText(aDatas.get(position).getTypename());
        tv_rm.setText(aDatas.get(position).getRemark());
        tv_time.setText(aDatas.get(position).getTime());
        tv_yuan.setText("¥" + aDatas.get(position).getMoney());

        view.setTag(aDatas.get(position).getId());
        return view;
    }


    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new AccountAdapter.AFilter();
        }
        return mFilter;
    }

    public class AFilter extends Filter {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {
            Filter.FilterResults result = new Filter.FilterResults();
            List<AccountBean> alist;
            if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                alist = backaccount;
            } else {//否则把符合条件的数据对象添加到集合中
                alist = new ArrayList<>();
                for (AccountBean account : backaccount) {
                    if (account.getRemark().contains(charSequence)||account.getTypename().contains(charSequence)) {
                        alist.add(account);
                    }
                }
            }
            result.values = alist; //将得到的集合保存到FilterResults的value变量中
            result.count = alist.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            aDatas = (List<AccountBean>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }

    }
}
