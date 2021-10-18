package com.qeecan.multical.Account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.qeecan.multical.Account.adapter.AccountAdapter;
import com.qeecan.multical.Account.bean.AccountBean;
import com.qeecan.multical.Account.db.AccountDao;
import com.qeecan.multical.Note.NoteEditActivity;
import com.qeecan.multical.Note.RecActivity;
import com.qeecan.multical.Note.bean.NoteBean;
import com.qeecan.multical.R;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Context context = this;
    ListView accountlv;
    AccountAdapter adapter;
    List<AccountBean> accountList = new ArrayList<AccountBean>();
    SearchView sv;

    private FloatingActionButton fbtn_to_Note, fbtn_account_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        fbtn_to_Note = findViewById(R.id.fbtn_to_Note);
        fbtn_to_Note.setOnClickListener(onClickListener);
        fbtn_account_add = findViewById(R.id.fbtn_account_add);
        fbtn_account_add.setOnClickListener(onClickListener);

        accountlv = findViewById(R.id.account_lv);
        adapter = new AccountAdapter(getApplicationContext(), accountList);
        //刷新
        refreshListView();
        accountlv.setAdapter(adapter);
        sv = findViewById(R.id.account_sv);
        getSearch();

        accountlv.setOnItemClickListener(this);
        accountlv.setOnItemLongClickListener(this);


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.fbtn_to_Note:
                    intent.setClass(AccountActivity.this, RecActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fbtn_account_add:
                    Intent intent1 = new Intent();
                    intent1.setClass(AccountActivity.this, AccountEditActivity.class);
                    intent1.putExtra("mode", 6);
                    startActivityForResult(intent1, 0);
                    break;

            }

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (requestCode == 1 && resultCode == RESULT_OK){//更新
            Bundle ub = data.getExtras();
            assert ub != null;
            String typename = ub.getString("typename");
            float money =ub.getFloat("money", 0);
            String remark = ub.getString("remark");
            String time =ub.getString("time");
            int kind = ub.getInt("kind", -1);
            //得到id信息
            long id = ub.getLong("id");
            AccountBean newAccount = new AccountBean(typename, money, remark, time, kind);
            AccountDao ad = new AccountDao(context);
            newAccount.setId(id);
            ad.open();
            ad.updateAccount(newAccount);
            ad.close();

        } else if (requestCode == 0 && resultCode == RESULT_OK)  {//新建
            Bundle b = data.getExtras();
            assert b != null;
            String typename =  b.getString("typename");
            float money =  b.getFloat("money", 0);
            String remark =  b.getString("remark");
            String time =  b.getString("time");
            int kind =  b.getInt("kind", -1);
            AccountBean account = new AccountBean(typename, money, remark, time, kind);
            AccountDao ad = new AccountDao(context);
            ad.open();
            ad.addAccount(account);
            ad.close();
        } else {
        }
        refreshListView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AccountBean account = (AccountBean) parent.getItemAtPosition(position);
        Intent intent = new Intent(AccountActivity.this, AccountEditActivity.class);
        intent.putExtra("typename", account.getTypename());
        intent.putExtra("money", account.getMoney());
        intent.putExtra("id", account.getId());
        intent.putExtra("remark", account.getRemark());
        intent.putExtra("time", account.getTime());
        intent.putExtra("kind", account.getKind());
        intent.putExtra("mode", 7);     //点击进入修改
        startActivityForResult(intent, 1);//收到回传码为1，收集更改过的数据
    }

    /*
     * 搜索实现
     * */
    private void getSearch() {
        sv.setQueryHint("search");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /*
             * 输入后提交实现搜索
             * */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /*
             * 实时搜索
             * */
            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d("shishi","nizuihaoshi");
                adapter.getFilter().filter(newText);
                return false;

            }
        });

    }


    /*
     * 列表刷新，使用户能实时看到更新过的内容
     * */
    public void refreshListView() {
        AccountDao ad = new AccountDao(context);
        ad.open();

        if (accountList.size() > 0) accountList.clear();
        accountList.addAll(ad.getAllAccount());
        ad.close();
        adapter.notifyDataSetChanged();
    }

    /*
     * 设置listview的长按事件，进行删除操作
     * */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final AccountBean account = accountList.get(position);
        new AlertDialog.Builder(AccountActivity.this)
                .setMessage("确定删除该条记录?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountDao op = new AccountDao(context);
                        op.open();
                        op.removeAccount(account);
                        op.close();
                        refreshListView();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
        return true;
    }


}
